package com.kryptos.data.repositories

import CoinFetchWorker
import android.content.Context
import androidx.work.*
import com.kryptos.data.database.AppDatabase
import com.kryptos.data.util.Constants.COIN_FETCHER_DATA_WORK_NAME
import com.kryptos.data.util.Constants.COIN_FETCHER_TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackerRepository @Inject constructor(@ApplicationContext private val context: Context) {
    var workManager: WorkManager = WorkManager.getInstance(context)

    init {

    }

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

    fun getTrackingData() = AppDatabase.getDatabase(context).cryptoDao().getAllData()

}