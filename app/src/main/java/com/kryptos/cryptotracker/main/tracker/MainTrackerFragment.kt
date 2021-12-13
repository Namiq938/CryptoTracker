package com.kryptos.cryptotracker.main.tracker

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.kryptos.core.BaseFragment
import com.kryptos.cryptotracker.R
import com.kryptos.cryptotracker.databinding.FragmentMainTrackerBinding

class MainTrackerFragment : BaseFragment<MainTrackerViewModel, FragmentMainTrackerBinding, Unit, MainTrackerEvent>(), View.OnClickListener {

    override fun getViewModelClass() = MainTrackerViewModel::class.java

    override val onViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainTrackerBinding
        get() = FragmentMainTrackerBinding::inflate

    override val onViewInit: FragmentMainTrackerBinding.() -> Unit = {

    }

    override fun onClick(view: View?) {
        when (view) {

        }
    }
}