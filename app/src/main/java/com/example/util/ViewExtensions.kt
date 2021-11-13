package com.example.util

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.view.View
import android.view.animation.BounceInterpolator
import com.example.presentation.SplashActivity

fun View.visible(visible: Boolean) {
  visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.animateDown(actionToEndOfAnim: () -> Unit) {
  val cordY = resources.displayMetrics.heightPixels.toFloat() / 2 + 150f
  animate().apply {
    translationY(cordY)
    interpolator = BounceInterpolator()
    duration = SplashActivity.DURATION_OF_ANIMATION
    setListener(
      object : AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {}
        override fun onAnimationEnd(animation: Animator?) {
          actionToEndOfAnim()
        }

        override fun onAnimationCancel(animation: Animator?) {}
        override fun onAnimationRepeat(animation: Animator?) {}
      }
    )
  }.start()
}