package com.example.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.algebra.soccernewtry.navdrawer.NavDrawerList
import com.example.R
import com.example.databinding.ActivityMainBinding
import com.example.presentation.ui.helper.auction.ConnectionHelper
import com.example.presentation.ui.leaderboards.viewmodel.LeaderboardsViewModel
import com.example.util.ConnectionLiveData
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private val navDrawerList = NavDrawerList(this)

    private val viewModelLeaderboards: LeaderboardsViewModel by viewModel()
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var connectionHelper: ConnectionHelper

    override fun onStart() {
        getMonth()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setDataValues()
        setToolbar()
        setNavHost()
    }

    private fun setDataValues() {
        connectionLiveData = ConnectionLiveData(context = this)
        connectionHelper = ConnectionHelper(connectionLiveData, this)
        binding.connection = connectionHelper
        connectionHelper.observeInternetConnection()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setToolbar() {
        (this as AppCompatActivity).setSupportActionBar(binding.toolbar)
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
        navDrawerList.setUpValues()
        navDrawerList.prepareListData()
    }

    private fun setNavHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()
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

    fun getMonth() {
        val job = viewModelLeaderboards.getRange("ranges")
        viewModelLeaderboards.listOfRanges.value?.let { result ->
            listOfMonth = result
        }
        getDifficulties()
    }

    private fun getDifficulties() {
        viewModelLeaderboards.getRange("difficulties")
        viewModelLeaderboards.listOfRanges.value?.let { result ->
            listOfDifficulties = result
        }
    }

    companion object {
        lateinit var listOfMonth: Map<Int, String>
        lateinit var listOfDifficulties: Map<Int, String>
    }
}
