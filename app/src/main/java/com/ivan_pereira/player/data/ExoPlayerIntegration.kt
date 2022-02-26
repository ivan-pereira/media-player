package com.ivan_pereira.player.data

import android.view.View
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.ivan_pereira.player.domain.MediaPlayer

class ExoPlayerIntegration(
  private val player: ExoPlayer
) : MediaPlayer {

  private companion object {
    const val INITIAL_POSITION = 0L
  }

  override fun setView(view: View) {
    (view as? PlayerView)?.player = this.player
  }

  override fun setSource(source: String) {
    val mediaItem = MediaItem.fromUri(source)
    player.addMediaItem(mediaItem)
  }

  override fun init() {
    player.prepare()
    player.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING;
    player.playWhenReady = true
  }

  override fun resume() {
    player.playWhenReady = true
    player.playbackState
  }

  override fun pauseAutoPlay() {
    player.playWhenReady = false
    player.playbackState
  }

  override fun play() {
    if (!player.isPlaying) {
      player.play()
    }
  }

  override fun pause() {
    onPlayerPlaying {
      player.pause()
    }
  }

  override fun reset() {
    player.seekTo(INITIAL_POSITION)
  }

  override fun increaseVolume() {
    onPlayerPlaying {
      player.increaseDeviceVolume()
    }
  }

  override fun decreaseVolume() {
    onPlayerPlaying {
      player.decreaseDeviceVolume()
    }
  }

  override fun increaseTime() {
    onPlayerPlaying {
      player.seekForward()
    }
  }

  override fun decreaseTime() {
    onPlayerPlaying {
      player.seekBack()
    }
  }

  private fun onPlayerPlaying(action: () -> Unit) {
    if (player.isPlaying) {
      action()
    }
  }
}