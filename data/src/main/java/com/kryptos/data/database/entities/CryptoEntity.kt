package com.kryptos.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kryptos.common.extensions.getTimeFromUnitTime
import com.kryptos.data.models.Bpi
import com.kryptos.data.models.CryptoModel
import com.kryptos.data.models.Rate
import com.kryptos.data.models.Time

@Entity(tableName = "crypto_items")
data class CryptoEntity(
    @PrimaryKey
    val id: Long,
    val rate: String?,
    val currencyCode: String?,
    val updatedAt: Long?
) {
    fun toDomain(): CryptoModel {
        return CryptoModel(Time(updatedAt?.getTimeFromUnitTime()), Bpi(Rate(rate, currencyCode)))
    }
}