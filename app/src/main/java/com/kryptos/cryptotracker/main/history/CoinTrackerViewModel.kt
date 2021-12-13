package com.kryptos.cryptotracker.main.history

import androidx.lifecycle.viewModelScope
import com.kryptos.core.BaseViewModel
import com.kryptos.data.models.CryptoModel
import com.kryptos.data.repositories.TrackerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinTrackerViewModel @Inject constructor(
    private val trackerRepository: TrackerRepository
) : BaseViewModel<CoinTrackerState, Unit>() {

    init {
        trackerRepository.getAppTrackingHistory().onEach {
            postState(CoinTrackerState.BitcoinHistory(it))
        }.launchIn(viewModelScope)
    }

}

sealed class CoinTrackerState {
    class BitcoinHistory(val list: List<CryptoModel>) : CoinTrackerState()
}


