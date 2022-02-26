package com.ivan_pereira.player.domain

import com.ivan_pereira.player.domain.events.DeviceMotionEvent
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingIn
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingLeft
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingOut
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingRight
import com.ivan_pereira.player.domain.events.DeviceMotionResult.NoMotionEvent
import com.ivan_pereira.player.domain.events.DeviceMotionResult.ShakeDetected
import com.ivan_pereira.player.domain.interactors.DeviceMovedMoreThanTenMeters
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@InternalCoroutinesApi
class DeviceEventsDelegate @Inject constructor(
  private val deviceMovedMoreThanTenMeters: DeviceMovedMoreThanTenMeters,
  private val motionEvent: DeviceMotionEvent,
  private val mediaPlayer: MediaPlayer
) {

  suspend fun subscribeLocationEvents() {
    deviceMovedMoreThanTenMeters.invoke().collect {
      mediaPlayer.reset()
      mediaPlayer.play()
    }
  }

  suspend fun subscribeMotionEvents() {
    motionEvent.onNewMotionEvent().collect { event ->
      when (event) {
        is MovingIn -> mediaPlayer.increaseTime()
        is MovingOut -> mediaPlayer.decreaseTime()
        is MovingRight -> mediaPlayer.increaseVolume()
        is MovingLeft -> mediaPlayer.decreaseVolume()
        ShakeDetected -> mediaPlayer.pause()
        NoMotionEvent -> {
          // no-op
        }
      }
    }
  }
}

