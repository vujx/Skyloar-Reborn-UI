package com.example.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.R
import com.example.databinding.ActivityMainBinding
import com.example.util.ConnectionLiveData
import com.example.util.visible

class MainActivity : AppCompatActivity() {

  companion object {
    var listOfMonth: Map<Int, String>? = null
    var listOfDifficulties: Map<Int, String>? = null
  }

  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController
  private lateinit var connectionLiveData: ConnectionLiveData

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setDataValues()
    setNavHost()
  }

  private fun setDataValues() {
    connectionLiveData = ConnectionLiveData(context = this)
    connectionLiveData.observe(
      this,
      {
        binding.textViewNetworkBanner.visible(!it)
      }
    )
  }

  private fun setNavHost() {
    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.frgNavBottom) as NavHostFragment
    navController = navHostFragment.findNavController()
    NavigationUI.setupWithNavController(
      binding.btmNav,
      navHostFragment.navController,
    )
  }
}
