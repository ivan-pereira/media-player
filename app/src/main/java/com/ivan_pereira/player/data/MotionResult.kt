package com.ivan_pereira.player.data

sealed class MotionResult {
  class AccelerometerResult(val data: FloatArray) : MotionResult()
  class GyroscopeResult(val data: FloatArray, val timestamp: Long) : MotionResult()
}