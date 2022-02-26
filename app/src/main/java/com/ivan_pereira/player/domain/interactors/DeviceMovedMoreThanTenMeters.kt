package com.ivan_pereira.player.domain.interactors

import android.location.Location
import com.ivan_pereira.player.domain.events.LocationEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class DeviceMovedMoreThanTenMeters @Inject constructor(
  private val locationEvent: LocationEvent
) {

  private companion object {
    const val DISTANCE_LIMIT = 10F
  }

  operator fun invoke(): Flow<Location> {
    return locationEvent.subscribeLocationEvents()
      .distinctUntilChanged(this::onConditionsMet)
  }

  private fun onConditionsMet(old: Location, new: Location): Boolean {
    return old.distanceTo(new) > DISTANCE_LIMIT
  }
}