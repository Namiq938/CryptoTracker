package com.kryptos.cryptotracker.main.tracker


import androidx.lifecycle.viewModelScope
import com.kryptos.core.BaseViewModel
import com.kryptos.data.models.CryptoModel
import com.kryptos.data.repositories.TrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainTrackerViewModel @Inject constructor(
    private val trackerRepository: TrackerRepository
) : BaseViewModel<MainTrackerState, MainTrackerEvent>() {

    init {
        trackerRepository.startPeriodicWorker()
        val max = trackerRepository.getMaxValue()
        val min = trackerRepository.getMinValue()
        trackerRepository.getLastRate()
            .onEach {
                postState(MainTrackerState.InitialValues(max, min, it))
            }.launchIn(viewModelScope)
    }

    fun setMaxValue(value: Float) {
        trackerRepository.setMaxValue(value)
        postEvent(MainTrackerEvent.MaxUpdate)
    }

    fun setMinValue(value: Float) {
        trackerRepository.setMinValue(value)
        postEvent(MainTrackerEvent.MinUpdate)
    }
}

sealed class MainTrackerEvent {
    object MaxUpdate : MainTrackerEvent()
    object MinUpdate : MainTrackerEvent()
}

sealed class MainTrackerState {
    class InitialValues(val maxValue: Float, val minValue: Float, val lastCryptoModel: CryptoModel?) : MainTrackerState()
}



