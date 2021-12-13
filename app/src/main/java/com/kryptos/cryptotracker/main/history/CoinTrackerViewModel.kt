package com.kryptos.cryptotracker.main.history
import com.kryptos.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainTrackerViewModel @Inject constructor(

) : BaseViewModel<Unit, MainTrackerEvent>() {

}

sealed class MainTrackerEvent {

}


