package com.kryptos.cryptotracker.main.tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.kryptos.core.BaseFragment
import com.kryptos.cryptotracker.R
import com.kryptos.cryptotracker.databinding.FragmentMainTrackerBinding
import com.kryptos.data.models.CryptoModel

class MainTrackerFragment : BaseFragment<MainTrackerViewModel, FragmentMainTrackerBinding, MainTrackerState, MainTrackerEvent>(), View.OnClickListener {

    override fun getViewModelClass() = MainTrackerViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainTrackerBinding
        get() = FragmentMainTrackerBinding::inflate

    override val onViewInit: FragmentMainTrackerBinding.() -> Unit = {
        setMaxButton.setOnClickListener(this@MainTrackerFragment)
        setMinButton.setOnClickListener(this@MainTrackerFragment)
        showHistory.setOnClickListener(this@MainTrackerFragment)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding.setMaxButton -> viewmodel.setMaxValue(binding.maxValueEdit.text.toString().toFloat())
            binding.setMinButton -> viewmodel.setMinValue(binding.minValueEdit.text.toString().toFloat())
            binding.showHistory -> findNavController().navigate(R.id.action_mainTrackerFragment_to_coinTrackHistoryFragment)
        }
    }

    override fun onEventUpdate(event: MainTrackerEvent) {
        when (event) {
            MainTrackerEvent.MaxUpdate -> Toast.makeText(requireContext(), getString(R.string.max_checking_value_set), Toast.LENGTH_SHORT).show()
            MainTrackerEvent.MinUpdate -> Toast.makeText(requireContext(), getString(R.string.min_checking_value_set), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStateUpdate(state: MainTrackerState) {
        when (state) {
            is MainTrackerState.InitialValues -> {
                setInitialValues(state.maxValue, state.minValue)
                state.lastCryptoModel?.let { setLastRate(it) }
            }
        }
    }

    private fun setLastRate(lastCryptoModel: CryptoModel) {
        binding.currentBitcoinRate.text = lastCryptoModel.bpi.usdRate.rate.toString()
    }

    private fun setInitialValues(maxValue: Float, minValue: Float) {
        withBinding {
            maxValueEdit.setText(maxValue.toString())
            minValueEdit.setText(minValue.toString())
        }
    }
}