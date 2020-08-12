package com.aligkts.cryptoexchange.ui.home

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showBottomNavigationView()
        viewModel.getCoins()
        registerObservers()
        initSpinners()
    }

    private fun initSpinners() {
        val coinItems = resources.getStringArray(R.array.CoinSpinnerItems)
        val spinnerAdapter = ArrayAdapter(requireActivity(),
            android.R.layout.simple_spinner_item,
            coinItems)
        binding.spnFirstSymbol.adapter = spinnerAdapter
        if (viewModel.genericSecureRepository.contains(FIRST_SYMBOL)) {
            binding.spnFirstSymbol.setSelection(viewModel.genericSecureRepository.getString(FIRST_SYMBOL)!!.getCoinSpinnerSelectedIndex())
        }
        binding.spnFirstSymbol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.genericSecureRepository.put(FIRST_SYMBOL, coinItems[position])
                binding.rvHome.adapter?.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.spnSecondSymbol.adapter = spinnerAdapter
        if (viewModel.genericSecureRepository.contains(SECOND_SYMBOL)) {
            binding.spnSecondSymbol.setSelection(viewModel.genericSecureRepository.getString(SECOND_SYMBOL)!!.getCoinSpinnerSelectedIndex())
        }
        binding.spnSecondSymbol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.genericSecureRepository.put(SECOND_SYMBOL, coinItems[position])
                binding.rvHome.adapter?.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun registerObservers() {
        viewModel.coins.observeNonNull(this) {
            setupCoinRecyclerview(it)
        }
    }

    private fun setupCoinRecyclerview(coins: List<CoinItemDTO>) {
        val coinAdapter = CoinAdapter {
            Toast.makeText(context, it.description, Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putParcelable(Constant.DETAIL_DATA, it)
            findNavController().navigate(R.id.action_home_to_detail, bundle)
        }
        coinAdapter.submitList(coins)
        binding.rvHome.apply {
            adapter = coinAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

}