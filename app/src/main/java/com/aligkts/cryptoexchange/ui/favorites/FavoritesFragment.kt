package com.aligkts.cryptoexchange.ui.favorites

import android.os.Bundle
import android.view.View
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseFragment
import com.aligkts.cryptoexchange.databinding.FragmentFavoritesBinding
import com.aligkts.cryptoexchange.ui.MainActivity

class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {

    override fun getViewModel() = FavoritesViewModel::class.java

    override fun getFragmentView() = R.layout.fragment_favorites

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).showBottomNavigationView()
    }

}