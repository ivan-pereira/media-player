package com.ivan_pereira.player.data

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate

@ExperimentalCoroutinesApi
class DataMotionDetector(
  private val sensorManager: SensorManager
) {

  private val gyroscopeSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
  private val accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

  fun onSensorChanged(): Flow<MotionResult> = callbackFlow {
    val sensorListener = object : SensorEventListener {
      override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
          Sensor.TYPE_ACCELEROMETER -> {
            val res = MotionResult.AccelerometerResult(event.values)
            trySend(res).isSuccess
          }
          Sensor.TYPE_GYROSCOPE -> {
            val res = MotionResult.GyroscopeResult(event.values)
            trySend(res).isSuccess
          }
        }
      }

      override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    sensorManager.registerListener(
      sensorListener,
      gyroscopeSensor,
      SensorManager.SENSOR_DELAY_NORMAL
    )

    sensorManager.registerListener(
      sensorListener,
      accelerometerSensor,
      SensorManager.SENSOR_DELAY_NORMAL
    )

    awaitClose {
      sensorManager.unregisterListener(sensorListener)
    }

  }.conflate()
}

