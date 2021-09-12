package com.example.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.R
import com.example.databinding.ActivityMainBinding
import com.example.presentation.ui.auctions.AuctionFragment
import com.example.presentation.ui.helper.auction.ConnectionHelper
import com.example.presentation.ui.leaderboards.fragments.PvEFragment
import com.example.presentation.ui.leaderboards.fragments.PvPFragment
import com.example.presentation.ui.stat.StatFragment
import com.example.util.ConnectionLiveData
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var connectionHelper: ConnectionHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setDataValues()
        setToolbar()
    }

    private fun setDataValues() {
        connectionLiveData = ConnectionLiveData(context = this)
        connectionHelper = ConnectionHelper(connectionLiveData, this)
        binding.connection = connectionHelper
        connectionHelper.observeInternetConnection()
    }

    private fun setToolbar() {
        (this as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        drawerToggle.syncState()
        binding.drawerLayout.addDrawerListener(drawerToggle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                val isOpen = binding.drawerLayout.isDrawerOpen(GravityCompat.START)
                if (!isOpen) binding.drawerLayout.openDrawer(GravityCompat.START)
                else binding.drawerLayout.closeDrawers()
                true
            }
            else -> false
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_auction -> supportFragmentManager.beginTransaction().replace(R.id.frg, AuctionFragment()).commit()
            R.id.nav_stat -> supportFragmentManager.beginTransaction().replace(R.id.frg, StatFragment()).commit()
            R.id.nav_leaderPvE -> supportFragmentManager.beginTransaction().replace(R.id.frg, PvEFragment()).commit()
            R.id.nav_leaderPvP -> supportFragmentManager.beginTransaction().replace(R.id.frg, PvPFragment()).commit()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    companion object{
        var listOfMonth: Map<Int, String>? = null
        var listOfDifficulties: Map<Int, String>? = null
    }
}
