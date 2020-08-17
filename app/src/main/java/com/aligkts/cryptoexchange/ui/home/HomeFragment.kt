package com.aligkts.cryptoexchange.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseFragment
import com.aligkts.cryptoexchange.databinding.FragmentHomeBinding
import com.aligkts.cryptoexchange.extension.getCoinSpinnerSelectedIndex
import com.aligkts.cryptoexchange.extension.observeNonNull
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.ui.MainActivity
import com.aligkts.cryptoexchange.util.Constant
import com.aligkts.cryptoexchange.util.Constant.Companion.FIRST_SYMBOL
import com.aligkts.cryptoexchange.util.Constant.Companion.SECOND_SYMBOL

/**
 * Created by Ali Göktaş on 11,August,2020
 */
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_home

    private val handler = Handler()
    val runnable = {
        setupObserver()
        scheduleReload()
    }

    private val coinAdapter by lazy {  CoinAdapter {
        val bundle = Bundle()
        //TODO if it is in favorite
        it.isFavorite = true
        bundle.putParcelable(Constant.DETAIL_DATA, it)
        findNavController().navigate(R.id.action_home_to_detail, bundle)
    }}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showBottomNavigationView()
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startPeriodicCoinRequests()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPeriodicCoinRequests()
    }

    private fun initUI() {
        val coinItems = resources.getStringArray(R.array.CoinSpinnerItems)
        val spinnerAdapter = ArrayAdapter(
            requireActivity(),
            R.layout.item_spinner,
            coinItems)
        binding.spnFirstSymbol.adapter = spinnerAdapter
        if (viewModel.genericSecureRepository.contains(FIRST_SYMBOL)) {
            binding.spnFirstSymbol.setSelection(
                viewModel.genericSecureRepository.getString(FIRST_SYMBOL)!!.getCoinSpinnerSelectedIndex()
            )
        }
        binding.spnFirstSymbol.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.genericSecureRepository.put(FIRST_SYMBOL, coinItems[position])
                    binding.rvHome.adapter?.notifyDataSetChanged()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        binding.spnSecondSymbol.adapter = spinnerAdapter
        if (viewModel.genericSecureRepository.contains(SECOND_SYMBOL)) {
            binding.spnSecondSymbol.setSelection(
                viewModel.genericSecureRepository.getString(
                    SECOND_SYMBOL
                )!!.getCoinSpinnerSelectedIndex()
            )
        }
        binding.spnSecondSymbol.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.genericSecureRepository.put(SECOND_SYMBOL, coinItems[position])
                    binding.rvHome.adapter?.notifyDataSetChanged()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        val lineDividerDrawable = ResourcesCompat.getDrawable(resources, R.drawable.line_divider, null)
        lineDividerDrawable?.let { drawable ->
            dividerItemDecoration.setDrawable(drawable)
        }
        binding.rvHome.apply {
            adapter = coinAdapter
            addItemDecoration(dividerItemDecoration)
        }
        runnable()
    }

    private fun setupObserver() {
        viewModel.coins.observeNonNull(this) {
            fillAdapter(it)
        }
    }

    private fun scheduleReload() {
        handler.postDelayed(runnable, 2000)
    }

    private fun fillAdapter(coins: List<CoinItemDTO>) {
        coinAdapter.items = coins
    }

}