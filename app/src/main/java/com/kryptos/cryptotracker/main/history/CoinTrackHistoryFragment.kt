package com.kryptos.cryptotracker.main.history

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kryptos.core.BaseFragment
import com.kryptos.cryptotracker.databinding.FragmentCoinTrackHistoryBinding
import com.kryptos.data.models.CryptoModel

class CoinTrackHistoryFragment : BaseFragment<CoinTrackerViewModel, FragmentCoinTrackHistoryBinding, CoinTrackerState, Unit>() {

    override fun getViewModelClass() = CoinTrackerViewModel::class.java
    private val adapter by lazy { CoinHistoryAdapter() }
    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCoinTrackHistoryBinding
        get() = FragmentCoinTrackHistoryBinding::inflate

    override val onViewInit: FragmentCoinTrackHistoryBinding.() -> Unit = {
        historyList.adapter = adapter
    }

    override fun onStateUpdate(state: CoinTrackerState) {
        when (state) {
            is CoinTrackerState.BitcoinHistory -> showTrackerHistory(state.list)
        }
    }

    private fun showTrackerHistory(list: List<CryptoModel>) {
        adapter.submitList(list)
    }

}