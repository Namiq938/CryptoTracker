import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kryptos.data.R
import com.kryptos.data.database.AppDatabase
import com.kryptos.data.device.NotificationHandler
import com.kryptos.data.io.RetrofitClient

class CoinFetchWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val applicationContext = applicationContext
        val api = RetrofitClient.getApi()
        val coinInfo = api.getCrypto()
        val db = AppDatabase.getDatabase(applicationContext)
        db.cryptoDao().insert(coinInfo.toEntity())
        Log.e(TAG, "Coin Fetch Successfull")
        NotificationHandler.makePushNotification(
            applicationContext.getString(R.string.crypto_alert_title),
            applicationContext.getString(R.string.crypto_alert_description_max),
            applicationContext
        )
        return Result.success()
    }

    companion object {
        private val TAG = CoinFetchWorker::class.java.simpleName
    }
}

