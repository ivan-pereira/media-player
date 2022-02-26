package com.ivan_pereira.player.domain.interactors

import android.hardware.SensorManager
import android.util.Log
import com.ivan_pereira.player.data.MotionResult.GyroscopeResult
import com.ivan_pereira.player.domain.events.DeviceMotionResult
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingIn
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingLeft
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingOut
import com.ivan_pereira.player.domain.events.DeviceMotionResult.MovingRight
import com.ivan_pereira.player.domain.events.DeviceMotionResult.OtherMovements
import javax.inject.Inject

class RotationDetector @Inject constructor() {

  companion object {
    const val POSITIVE_MOVEMENT_LIMIT = 0.4f
    const val NEGATIVE_MOVEMENT_LIMIT = -0.4f
  }
  //keep track of last time

  fun resolved(eventResult: GyroscopeResult): DeviceMotionResult {
    val x = Math.toDegrees(eventResult.data[0].toDouble()).toInt()
    val z = Math.toDegrees(eventResult.data[2].toDouble()).toInt()
    if (z > 10 || z < -10) {
      Log.d("DEBUG_TAG_GYRO", "resolve:  z $z ")
    }
    if (x > 10 || x < -10) {
      Log.d("DEBUG_TAG_GYRO", "resolve:  x $x ")
    }


    return when {
      z > NEGATIVE_MOVEMENT_LIMIT -> MovingIn
      z < NEGATIVE_MOVEMENT_LIMIT -> MovingOut
      x > POSITIVE_MOVEMENT_LIMIT -> MovingRight
      x > NEGATIVE_MOVEMENT_LIMIT -> MovingLeft
      else -> OtherMovements
    }
  }

  fun resolve(eventResult: GyroscopeResult): OtherMovements {
    val rotationMatrix = FloatArray(16)
    SensorManager.getRotationMatrixFromVector(
      rotationMatrix,
      eventResult.data
    )
    val remappedRotationMatrix = FloatArray(16)
    SensorManager.remapCoordinateSystem(
      rotationMatrix,
      SensorManager.AXIS_X,
      SensorManager.AXIS_Z,
      remappedRotationMatrix
    )
    val orientations = FloatArray(3)
    SensorManager.getOrientation(remappedRotationMatrix, orientations)
    for (i in 0..2) {
      orientations[i] = Math.toDegrees(orientations[i].toDouble()).toFloat()
    }
    val result = orientations[2]

    Log.d("ROTATION_TAG", "resolve: rotation $result")

    return OtherMovements
  }
}