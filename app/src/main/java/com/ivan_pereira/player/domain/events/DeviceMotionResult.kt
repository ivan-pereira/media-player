package com.ivan_pereira.player.domain.events

sealed class DeviceMotionResult {
  object MovingLeft : DeviceMotionResult()
  object MovingRight : DeviceMotionResult()
  object MovingIn : DeviceMotionResult()
  object MovingOut : DeviceMotionResult()
  object ShakeDetected : DeviceMotionResult()
  object NoMotionEvent : DeviceMotionResult()
}