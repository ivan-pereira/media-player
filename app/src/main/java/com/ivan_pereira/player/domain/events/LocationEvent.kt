package com.ivan_pereira.player.domain.events

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationEvent {

  fun subscribeLocationEvents(): Flow<Location>
}