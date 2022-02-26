package com.ivan_pereira.player.data

import android.content.Context
import android.hardware.SensorManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.gms.location.LocationServices
import com.ivan_pereira.player.domain.MediaPlayer
import com.ivan_pereira.player.domain.events.LocationEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
@InstallIn(SingletonComponent::class)
@ExperimentalCoroutinesApi
object DataModule {

  @Provides
  fun provideLocationEvent(@ApplicationContext context: Context): LocationEvent {
    val client =
      LocationServices.getFusedLocationProviderClient(context)

    return DataLocationManager(client)
  }

  @Provides
  fun provideMotionDetector(@ApplicationContext context: Context): DataMotionDetector {
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    return DataMotionDetector(sensorManager)
  }

  @Provides
  fun provideMediaPlayer(@ApplicationContext context: Context): MediaPlayer {
    val player = ExoPlayer.Builder(context).build()
    return ExoPlayerIntegration(player)
  }
}