package com.ivan_pereira.player.data

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.ivan_pereira.player.domain.events.LocationEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@SuppressLint("MissingPermission")
class DataLocationManager(
  private val client: FusedLocationProviderClient
) : LocationEvent {

  private companion object {
    const val UPDATE_INTERVAL_SECS = 10L
    const val FASTEST_UPDATE_INTERVAL_SECS = 2L
  }

  override fun subscribeLocationEvents(): Flow<Location> = callbackFlow {
    val locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult) {
        super.onLocationResult(locationResult)
        val location = locationResult.lastLocation
        this@callbackFlow.trySend(location).isSuccess
      }
    }

    client.requestLocationUpdates(
      createLocationRequest(),
      locationCallback,
      Looper.getMainLooper()
    )

    awaitClose { client.removeLocationUpdates(locationCallback) }
  }

  private fun createLocationRequest(): LocationRequest {
    return LocationRequest.create().apply {
      interval = TimeUnit.SECONDS.toMillis(UPDATE_INTERVAL_SECS)
      fastestInterval = TimeUnit.SECONDS.toMillis(FASTEST_UPDATE_INTERVAL_SECS)
      priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
  }
}

