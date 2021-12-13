import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kryptos.data.R
import com.kryptos.data.database.AppDatabase
import com.kryptos.data.device.NotificationHandler
import com.kryptos.data.io.RetrofitClient
import com.kryptos.data.models.CryptoModel
import com.kryptos.data.prefs.MainSharedPreferences
import com.kryptos.data.util.Constants

class CoinFetchWorker(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val applicationContext = applicationContext
        val api = RetrofitClient.getApi()
        val coinInfo = api.getCrypto()
        val db = AppDatabase.getDatabase(applicationContext)
        db.cryptoDao().insert(coinInfo.toEntity())
        Log.e(TAG, "Coin Fetch Successfull")
        showNotification(coinInfo)
        return Result.success()
    }

    private fun showNotification(coinInfo: CryptoModel) {
        with(MainSharedPreferences(applicationContext)) {
            val maxValue = get(Constants.MAX_VALUE, -1f)
            val minValue = get(Constants.MIN_VALUE, -1f)
            val currencyRate = coinInfo.bpi.usdRate.rate?.toFloat() ?: -1f
            if (maxValue > 0 && currencyRate > maxValue)
                NotificationHandler.makePushNotification(
                    applicationContext.getString(R.string.crypto_alert_title),
                    applicationContext.getString(R.string.crypto_alert_description_max),
                    applicationContext
                )
            else if (minValue > 0 && currencyRate < minValue)
                NotificationHandler.makePushNotification(
                    applicationContext.getString(R.string.crypto_alert_title),
                    applicationContext.getString(R.string.crypto_alert_description_min),
                    applicationContext
                )
        }

    }

    companion object {
        private val TAG = CoinFetchWorker::class.java.simpleName
    }
}

