package com.ivan_pereira.player.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ui.PlayerView
import com.ivan_pereira.player.R.id
import com.ivan_pereira.player.R.layout
import com.ivan_pereira.player.domain.MediaPlayer
import com.ivan_pereira.player.domain.interactors.InitMediaPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@InternalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  @Inject
  lateinit var player: MediaPlayer

  @Inject
  lateinit var initMediaPlayer: InitMediaPlayer

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    this.setFullScreen()
    this.hideActionBar()
    val playerView = findViewById<PlayerView>(id.exoPlayer)
    viewModel.init()
    initMediaPlayer.with(playerView)
  }

  override fun onPause() {
    super.onPause()
    player.pauseAutoPlay()
  }

  override fun onResume() {
    super.onResume()
    player.resume()
  }
}

