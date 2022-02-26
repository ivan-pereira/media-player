package com.ivan_pereira.player.domain.events

import com.ivan_pereira.player.PlayerApplication_HiltComponents.SingletonC
import com.ivan_pereira.player.data.DataMotionDetector
import com.ivan_pereira.player.domain.interactors.DeviceMotionEventsController
import com.ivan_pereira.player.domain.interactors.RotationDetector
import com.ivan_pereira.player.domain.interactors.ShakeDetector
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
object DomainModule {

  @Provides
  fun provideDeviceMotionEvent(
    dataMotionDetector: DataMotionDetector,
    shakeDetector: ShakeDetector,
    rotationDetector: RotationDetector
  ): DeviceMotionEvent {
    return DeviceMotionEventsController(dataMotionDetector,shakeDetector,rotationDetector)
  }
}