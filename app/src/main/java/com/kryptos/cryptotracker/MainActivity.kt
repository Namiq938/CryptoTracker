package com.kryptos.cryptotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.kryptos.cryptotracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var mainActivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this)).also {
            setContentView(it.root)
        }
    }
}