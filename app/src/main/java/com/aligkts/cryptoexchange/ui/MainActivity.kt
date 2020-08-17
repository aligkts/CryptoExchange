package com.aligkts.cryptoexchange.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.util.Constant
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_home)
                }
                R.id.navigation_favorites -> {
                    val bundle = Bundle()
                    bundle.putBoolean(Constant.OPEN_FAVORITES, true)
                    findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_home, bundle)
                }
            }
            true
        }
    }

    fun hideBottomNavigationView() {
        with(bottom_navigation_view) {
            if (visibility == View.VISIBLE && alpha == 1f) {
                animate()
                    .alpha(0f)
                    .withEndAction { visibility = View.GONE }
                    .duration = 1000
            }
        }
    }

    fun showBottomNavigationView() {
        with(bottom_navigation_view) {
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .duration = 1000
        }
    }
}