package com.ivan_pereira.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivan_pereira.player.domain.DeviceEventsDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@InternalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
  private val deviceEventsDelegate: DeviceEventsDelegate
) : ViewModel() {

  fun init() {
    viewModelScope.launch {
      deviceEventsDelegate.subscribeLocationEvents()
      deviceEventsDelegate.subscribeMotionEvents()
    }
  }
}