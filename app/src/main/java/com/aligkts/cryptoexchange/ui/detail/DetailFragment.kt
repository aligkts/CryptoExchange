package com.aligkts.cryptoexchange.ui.detail

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseFragment
import com.aligkts.cryptoexchange.databinding.FragmentDetailBinding
import com.aligkts.cryptoexchange.extension.observeNonNull
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.ui.MainActivity
import com.aligkts.cryptoexchange.util.Constant

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override fun getViewModel() = DetailViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_detail

    private val handler = Handler()
    val runnable = {
        observeCoinDetails()
        scheduleReload()
    }

    lateinit var code: String
    private val coinDetailAdapter by lazy { CoinDetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coin = arguments?.getParcelable<CoinItemDTO>(Constant.DETAIL_DATA)
        (activity as MainActivity).hideBottomNavigationView()
        (activity as MainActivity).supportActionBar?.title = coin?.code
        coin?.let {
            code = it.code
        }
        binding.rvDetail.apply {
            adapter = coinDetailAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
        runnable()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startPeriodicCoinDetailRequests(code)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPeriodicCoinDetailRequests()
    }

    private fun observeCoinDetails() {
        viewModel.coinDetail.observeNonNull(this) {
            fillAdapter(it)
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
        return inflater.inflate(R.menu.menu_favorite, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_favorite -> {
                Toast.makeText(context, "Favorite clicked", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}