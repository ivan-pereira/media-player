package com.ivan_pereira.player.domain.events

import kotlinx.coroutines.flow.Flow

interface DeviceMotionEvent {

  fun onNewMotionEvent(): Flow<DeviceMotionResult>
}