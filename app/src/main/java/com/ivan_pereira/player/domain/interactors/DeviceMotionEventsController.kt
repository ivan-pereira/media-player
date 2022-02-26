package com.ivan_pereira.player.domain.interactors

import com.ivan_pereira.player.data.DataMotionDetector
import com.ivan_pereira.player.data.MotionResult.AccelerometerResult
import com.ivan_pereira.player.data.MotionResult.GyroscopeResult
import com.ivan_pereira.player.domain.events.DeviceMotionEvent
import com.ivan_pereira.player.domain.events.DeviceMotionResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi class DeviceMotionEventsController @Inject constructor(
  private val dataMotionDetector: DataMotionDetector,
  private val shakeDetector: ShakeDetector,
  private val rotationDetector: RotationDetector
) : DeviceMotionEvent {

  override fun onNewMotionEvent(): Flow<DeviceMotionResult> {
    return dataMotionDetector.onSensorChanged().map { eventResult ->
      when (eventResult) {
        is AccelerometerResult -> shakeDetector.resolve(eventResult)
        is GyroscopeResult -> rotationDetector.resolve(eventResult)
      }
    }
  }
}