package com.aligkts.cryptoexchange.ui

import android.os.Bundle
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
}