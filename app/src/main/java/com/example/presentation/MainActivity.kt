package com.example.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.R
import com.example.databinding.ActivityMainBinding
import com.example.util.ConnectionLiveData
import com.example.util.visible

class MainActivity : AppCompatActivity() {

  companion object {

    var listOfMonth: Map<Int, String>? = null
    var listOfDifficulties: Map<Int, String>? = null
    var homeIntroText = ""
  }

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController
  private lateinit var connectionLiveData: ConnectionLiveData

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setSupportActionBar(binding.toolbar)
    setDataValues()
    setNavHost()
    val appBarConfiguration = AppBarConfiguration
      .Builder(
        R.id.homeFragment,
        R.id.statFragment,
        R.id.auctionFragment,
        R.id.docFragment,
        R.id.leaderboardsFragment,
      )
      .build()
    setupActionBarWithNavController(navController, appBarConfiguration)
  }

  private fun setDataValues() {
    connectionLiveData = ConnectionLiveData(context = this)
    connectionLiveData.observe(
      this,
      {
      }
    )
  }

  override fun onSupportNavigateUp(): Boolean {
    return navController.navigateUp() || super.onSupportNavigateUp()
  }

  private fun setNavHost() {
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.frgNavBottom) as NavHostFragment
    navController = navHostFragment.findNavController()
    NavigationUI.setupWithNavController(
      binding.btmNav,
      navHostFragment.navController,
    )

    navController.addOnDestinationChangedListener { _, destination, _ ->
      binding.toolbar.navigationIcon = null
      when (destination.id) {
        R.id.leaderboardsFragment -> binding.btmNav.visible(true)
        R.id.pvEFragment -> binding.btmNav.visible(false)
        else -> binding.btmNav.visible(true)
      }
    }
  }
}
