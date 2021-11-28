package com.example.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.databinding.ActivitySplashBinding
import com.example.util.animateDown

class SplashActivity : AppCompatActivity() {

  companion object {

    const val DURATION_OF_ANIMATION = 1400L
  }

  private lateinit var binding: ActivitySplashBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivitySplashBinding.inflate(layoutInflater)
    setContentView(binding.root)

    splashImageAnimation()
  }

  private fun splashImageAnimation() {
    binding.splashScreenImage.animateDown { startHomeScreen() }
  }

  private fun startHomeScreen() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
  }
}
