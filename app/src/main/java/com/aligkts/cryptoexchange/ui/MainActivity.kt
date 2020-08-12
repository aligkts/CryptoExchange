package com.aligkts.cryptoexchange.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import com.aligkts.cryptoexchange.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener {item ->
            onNavDestinationSelected(item, Navigation.findNavController(this, R.id.nav_host_fragment))
        }
    }

    fun hideBottomNavigationView() {
        with(bottomNavigation) {
            if (visibility == View.VISIBLE && alpha == 1f) {
                animate()
                    .alpha(0f)
                    .withEndAction { visibility = View.GONE }
                    .duration = 1000
            }
        }
    }

    fun showBottomNavigationView() {
        with(bottomNavigation) {
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .duration = 1000
        }
    }
}