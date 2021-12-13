package com.kryptos.data.io

import com.kryptos.data.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        private val INSTANCE: Retrofit? = null
        private val logger = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }
        private val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()

        private fun getRetrofit(): Retrofit {
            return INSTANCE ?: Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getApi(): Api {
            return getRetrofit().create(Api::class.java)
        }
    }

}