package com.kryptos.data.models

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import com.kryptos.common.extensions.getUnixTime
import com.kryptos.data.database.entities.CryptoEntity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class CryptoModel(
    @SerializedName("time")
    val time: Time,
    @SerializedName("bpi")
    val bpi: Bpi
) {
    @SuppressLint("SimpleDateFormat")
    fun toEntity(): CryptoEntity {
        val currTime = System.currentTimeMillis()
        return (CryptoEntity(currTime, bpi.usdRate.rate, bpi.usdRate.currencyCode, time.updatedAt?.getUnixTime()))
    }
}

data class Bpi(
    @SerializedName("USD")
    val usdRate: Rate
)


data class Rate(
    @SerializedName("rate_float")
    val rate: Float?,
    @SerializedName("code")
    val currencyCode: String?
)

data class Time(
    @SerializedName("updated")
    val updatedAt: String?
)
