package com.ivan_pereira.player.domain.interactors

import com.ivan_pereira.player.data.MotionResult.GyroscopeResult
import com.ivan_pereira.player.domain.events.DeviceMotionResult
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingIn
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingLeft
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingOut
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingRight
import com.ivan_pereira.player.domain.events.DeviceMotionResult.NoMotionEvent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RotationDetector @Inject constructor() {

  companion object {
    const val Z_POSITIVE_MOVEMENT_LIMIT = 15f
    const val Z_NEGATIVE_MOVEMENT_LIMIT = -15f
    const val X_POSITIVE_MOVEMENT_LIMIT = 15f
    const val X_NEGATIVE_MOVEMENT_LIMIT = -15f
    const val THRESHOLD_FOR_NEW_EVENT_MS = 1600
  }

  var lastTimestamp = 0L

  fun resolve(eventResult: GyroscopeResult): DeviceMotionResult {
    val x = Math.toDegrees(eventResult.data[0].toDouble()).toInt()
    val z = Math.toDegrees(eventResult.data[2].toDouble()).toInt()
    val timestamp: Long = TimeUnit.MILLISECONDS.convert(eventResult.timestamp, TimeUnit.NANOSECONDS)
    val difference = (timestamp - lastTimestamp) > THRESHOLD_FOR_NEW_EVENT_MS

    var res: DeviceMotionResult = NoMotionEvent

    if (difference || lastTimestamp == 0L) {
      res = when {
        z > Z_POSITIVE_MOVEMENT_LIMIT -> {
          setTimestamp(timestamp)
          MovingLeft
        }
        z < Z_NEGATIVE_MOVEMENT_LIMIT -> {
          setTimestamp(timestamp)
          MovingRight
        }
        x > X_POSITIVE_MOVEMENT_LIMIT -> {
          setTimestamp(timestamp)
          MovingIn
        }
        x > X_NEGATIVE_MOVEMENT_LIMIT -> {
          setTimestamp(timestamp)
          MovingOut
        }
        else -> {
          NoMotionEvent
        }
      }
    }

    return res
  }

  private fun setTimestamp(timestamp: Long) {
    lastTimestamp = timestamp
  }
}