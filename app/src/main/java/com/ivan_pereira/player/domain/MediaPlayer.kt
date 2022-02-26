package com.ivan_pereira.player.domain

import android.view.View

interface MediaPlayer {

  fun setView(view : View)
  fun setSource(source: String)
  fun init()

  fun resume()
  fun pauseAutoPlay()
  fun pause()
  fun play()
  fun reset()
  fun increaseVolume()
  fun decreaseVolume()
  fun increaseTime()
  fun decreaseTime()
}