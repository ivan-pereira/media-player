package com.ivan_pereira.player.domain.interactors

import android.view.View
import com.ivan_pereira.player.domain.MediaPlayer
import javax.inject.Inject

class InitMediaPlayer @Inject constructor(
  private val player: MediaPlayer
) {

  private companion object {
    const val SOURCE =
      "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"
  }

  fun with(view: View) {
    player.setView(view)
    player.setSource(SOURCE)
    player.init()
  }
}