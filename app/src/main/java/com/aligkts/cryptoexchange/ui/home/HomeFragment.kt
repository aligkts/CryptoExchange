package com.aligkts.cryptoexchange.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseFragment
import com.aligkts.cryptoexchange.base.GenericAdapter
import com.aligkts.cryptoexchange.databinding.FragmentHomeBinding
import com.aligkts.cryptoexchange.databinding.ItemCoinBinding
import com.aligkts.cryptoexchange.extension.observeNonNull
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCoins()
        registerObservers()
    }

    private fun registerObservers() {
        viewModel.coins.observeNonNull(this) {
            setupCoinRecyclerview(it)
        }
    }

    private fun setupCoinRecyclerview(coins: List<CoinItemDTO>) {
        val adapter = CoinAdapter()
        adapter.submitList(coins)
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.adapter = adapter
        /*binding.rvHome.adapter = GenericAdapter(coins, { holder, model ->
                when (holder.binding) {
                    is ItemCoinBinding -> {
                        with(holder.binding) {
                            item = model
                        }
                    }
                }
            }, { clickedCoin ->
                Toast.makeText(context, clickedCoin.code, Toast.LENGTH_SHORT).show()
            })*/
    }

}