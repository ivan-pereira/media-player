package com.ivan_pereira.player.domain.interactors

import com.ivan_pereira.player.data.MotionResult.AccelerometerResult
import com.ivan_pereira.player.domain.events.DeviceMotionResult
import com.ivan_pereira.player.domain.events.DeviceMotionResult.NoMotionEvent
import com.ivan_pereira.player.domain.events.DeviceMotionResult.ShakeDetected
import javax.inject.Inject

class ShakeDetector @Inject constructor() {

  companion object {
    const val ACCELERATION_LIMIT = 12
  }

  private var acceleration = 0f
  private var currentAcceleration = 0f
  private var lastAcceleration = 0f

  fun resolve(eventResult: AccelerometerResult): DeviceMotionResult {
    val x = eventResult.data[0]
    val y = eventResult.data[1]
    val z = eventResult.data[2]
    lastAcceleration = currentAcceleration

    currentAcceleration = kotlin.math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
    val delta: Float = currentAcceleration - lastAcceleration
    acceleration = acceleration * 0.9f + delta

    if (acceleration > ACCELERATION_LIMIT) {
      return ShakeDetected
    }

    return NoMotionEvent
  }
}