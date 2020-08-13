package com.aligkts.cryptoexchange.ui.detail

import android.os.Bundle
import android.view.View
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseFragment
import com.aligkts.cryptoexchange.base.GenericAdapter
import com.aligkts.cryptoexchange.databinding.FragmentDetailBinding
import com.aligkts.cryptoexchange.databinding.ItemCoinDetailBinding
import com.aligkts.cryptoexchange.extension.observeNonNull
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.ui.MainActivity
import com.aligkts.cryptoexchange.util.Constant

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>() {

    override fun getViewModel() = DetailViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coin = arguments?.getParcelable<CoinItemDTO>(Constant.DETAIL_DATA)
        (activity as MainActivity).hideBottomNavigationView()
        (activity as MainActivity).supportActionBar?.title = coin?.code
        coin?.let {
            viewModel.getCoinDetails(it.code)
        }
        viewModel.coinDetail.observeNonNull(this) {
            setupDetailRecyclerview(it)
        }

    }

    private fun setupDetailRecyclerview(detailItems: List<DItem>) {
        binding.rvDetail.adapter = GenericAdapter(detailItems, { holder, model ->
                when (holder.binding) {
                    is ItemCoinDetailBinding -> {
                        with(holder.binding) {
                            item = model
                        }
                    }
                }
            }, null)
    }

}