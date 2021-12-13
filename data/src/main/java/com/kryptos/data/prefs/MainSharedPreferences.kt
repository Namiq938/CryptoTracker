package com.kryptos.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.kryptos.data.util.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainSharedPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val filename: String = Constants.MAIN_SHARED_PREFS

    val prefs: SharedPreferences by lazy {
        val key = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
        EncryptedSharedPreferences.create(
            context, filename, key,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


    fun clear() {
        val editor = prefs.edit()
        editor.clear()
        editor?.apply()
    }

    fun remove(item: String) {
        val editor = prefs.edit()
        editor.remove(item)
        editor?.apply()
    }

    fun <T> set(item: String, value: T) {
        val editor = prefs.edit()

        when (value) {
            is Int -> {
                editor?.putInt(item, value)
            }
            is Long -> {
                editor?.putLong(item, value)
            }
            is Float -> {
                editor?.putFloat(item, value)
            }
            is String -> {
                editor?.putString(item, value)
            }
            is Boolean -> {
                editor?.putBoolean(item, value)
            }
        }
        editor?.apply()
    }

    inline fun <reified T> get(item: String, default: T): T {
        return when (default) {
            is Int -> {
                prefs.getInt(item, default as Int) as T
            }
            is Long -> {
                prefs.getLong(item, default as Long) as T
            }
            is Float -> {
                prefs.getFloat(item, default as Float) as T
            }
            is String -> {
                prefs.getString(item, default as String) as T
            }
            is Boolean -> {
                prefs.getBoolean(item, default as Boolean) as T
            }
            else -> {
                prefs.getString(item, default as String) as T
            }
        }
    }
}