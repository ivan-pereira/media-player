package com.ivan_pereira.player.presentation

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

fun Activity.setFullScreen() {
  when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
      window.attributes.layoutInDisplayCutoutMode =
        WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
      window.setDecorFitsSystemWindows(false)
      window.insetsController?.apply {
        hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
      }
    }
    else -> {
      @Suppress("DEPRECATION")
      window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
          or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          or View.SYSTEM_UI_FLAG_IMMERSIVE
          or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
          or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }
  }
}

fun AppCompatActivity.hideActionBar() {
  this.supportActionBar?.hide()
}