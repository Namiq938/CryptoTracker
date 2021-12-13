package com.kryptos.data.repositories

import CoinFetchWorker
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.kryptos.data.database.AppDatabase
import com.kryptos.data.models.CryptoModel
import com.kryptos.data.prefs.MainSharedPreferences
import com.kryptos.data.util.Constants
import com.kryptos.data.util.Constants.COIN_FETCHER_DATA_WORK_NAME
import com.kryptos.data.util.Constants.COIN_FETCHER_TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackerRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mainSharedPreferences: MainSharedPreferences
) {
    var workManager: WorkManager = WorkManager.getInstance(context)

    fun startPeriodicWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val periodicSyncDataWork = PeriodicWorkRequest.Builder(CoinFetchWorker::class.java, 15, TimeUnit.MINUTES)
            .addTag(COIN_FETCHER_TAG)
            .setConstraints(constraints)
            .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueueUniquePeriodicWork(
            COIN_FETCHER_DATA_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncDataWork
        )
    }

    fun getAppTrackingHistory(): Flow<List<CryptoModel>> =
        AppDatabase.getDatabase(context).cryptoDao().getAllData()
            .map {
                it.map { item ->
                    item.toDomain()
                }
            }


    fun getMinValue(): Float {
        return mainSharedPreferences.get(Constants.MIN_VALUE, 0f)
    }

    fun getMaxValue(): Float {
        return mainSharedPreferences.get(Constants.MAX_VALUE, 0f)
    }

    fun getLastRate(): Flow<CryptoModel?> {
        return AppDatabase.getDatabase(context).cryptoDao().getLastItem().map {
            it?.toDomain()
        }
    }

    fun setMaxValue(value: Float) {
        mainSharedPreferences.set(Constants.MAX_VALUE, value)
    }

    fun setMinValue(value: Float) {
        mainSharedPreferences.set(Constants.MIN_VALUE, value)
    }
}