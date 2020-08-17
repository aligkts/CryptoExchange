package com.aligkts.cryptoexchange.ui.detail

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseFragment
import com.aligkts.cryptoexchange.databinding.FragmentDetailBinding
import com.aligkts.cryptoexchange.extension.observeNonNull
import com.aligkts.cryptoexchange.model.dto.response.CoinGraphResponse
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.ui.MainActivity
import com.aligkts.cryptoexchange.util.Constant
import com.aligkts.cryptoexchange.util.DateAxisValueFormatter
import com.aligkts.cryptoexchange.util.GraphMarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter

/**
 * Created by Ali Göktaş on 14,August,2020
 */

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override fun getViewModel() = DetailViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_detail

    private val handler = Handler()
    val runnable = {
        registerObservers()
        scheduleReload()
    }

    lateinit var code: String
    lateinit var coin: CoinItemDTO

    private val coinDetailAdapter by lazy { CoinDetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<CoinItemDTO>(Constant.DETAIL_DATA)?.let {
            coin = it
        }
        (activity as MainActivity).hideBottomNavigationView()
        (activity as MainActivity).supportActionBar?.title = coin.code
        code = coin.code
        initUI()
        viewModel.getCoinGraphData(code)
    }

    private fun initUI() {
        binding.rvDetail.apply {
            adapter = coinDetailAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        runnable()

        binding.chartCoinDetail.setViewPortOffsets(0f, 0f, 0f, 0f)
        binding.chartCoinDetail.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorLightGrey))
        binding.chartCoinDetail.description.isEnabled = false
        binding.chartCoinDetail.setTouchEnabled(true)
        binding.chartCoinDetail.isDragEnabled = true
        binding.chartCoinDetail.setScaleEnabled(false)
        binding.chartCoinDetail.setPinchZoom(false)
        binding.chartCoinDetail.setDrawGridBackground(false)
        binding.chartCoinDetail.maxHighlightDistance = 300f

        val xAxis: XAxis = binding.chartCoinDetail.xAxis
        xAxis.setLabelCount(4, false)
        xAxis.textColor = Color.WHITE
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        binding.chartCoinDetail.xAxis.valueFormatter = DateAxisValueFormatter()

        val yAxis: YAxis = binding.chartCoinDetail.axisLeft
        yAxis.setLabelCount(6, false)
        yAxis.textColor = Color.WHITE
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxis.setDrawGridLines(false)
        yAxis.axisLineColor = Color.WHITE


        val markerView = GraphMarkerView(requireContext(), R.layout.custom_marker_view)
        markerView.chartView = binding.chartCoinDetail
        binding.chartCoinDetail.marker = markerView
        binding.chartCoinDetail.axisRight.isEnabled = false
        binding.chartCoinDetail.legend.isEnabled = false
        binding.chartCoinDetail.animateXY(2000, 2000)
        binding.chartCoinDetail.invalidate()
    }

    private fun setGraphData(graphData: CoinGraphResponse) {
        val values = ArrayList<Entry>()
        for (i in (0 until graphData.size)) {
            values.add(Entry(graphData[i].time.toFloat(), graphData[i].price.toFloat()))
        }
        val set1: LineDataSet
        if (binding.chartCoinDetail.data != null && binding.chartCoinDetail.data.dataSetCount > 0) {
            set1 = binding.chartCoinDetail.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            binding.chartCoinDetail.data.notifyDataChanged()
            binding.chartCoinDetail.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, "Coin DataSet")
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.WHITE)
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.color = Color.WHITE
            set1.fillColor = Color.WHITE
            set1.fillAlpha = 100
            set1.setDrawHorizontalHighlightIndicator(false)
            set1.fillFormatter = IFillFormatter { _, _ -> binding.chartCoinDetail.axisLeft.axisMinimum }

            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)
            binding.chartCoinDetail.data = data
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.startPeriodicCoinDetailRequests(code)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPeriodicRequest()
    }

    private fun registerObservers() {
        viewModel.coinDetail.observeNonNull(this) {
            fillAdapter(it)
        }
        viewModel.coinGraphData.observeNonNull(this) {
            setGraphData(it)
        }
    }

    private fun scheduleReload() {
        handler.postDelayed(runnable, 2000)
    }

    private fun fillAdapter(coinDetails: List<DItem>) {
        coinDetailAdapter.items = coinDetails
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_favorite, menu)
        if (coin.isFavorite) {
            menu.findItem(R.id.action_favorite).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_selected)
        } else {
            menu.findItem(R.id.action_favorite).icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_unselected)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                if (coin.isFavorite) {
                    item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_unselected)
                    coin.isFavorite = false
                    viewModel.deleteFavoriteCoin(coin.id)
                } else {
                    item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_selected)
                    coin.isFavorite = true
                    viewModel.addFavoriteCoin(coin.id)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}