package com.kryptos.cryptotracker.main.tracker


import androidx.lifecycle.viewModelScope
import com.kryptos.core.BaseViewModel
import com.kryptos.data.repositories.TrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainTrackerViewModel @Inject constructor(
    private val trackerRepository: TrackerRepository
) : BaseViewModel<Unit, MainTrackerEvent>() {

    init {
        trackerRepository.startPeriodicWorker()
    }

    fun getCoinHistory() {
        viewModelScope.launch {
            trackerRepository.getTrackingData().collect {
                it
            }
        }
    }
}

sealed class MainTrackerEvent {

}


