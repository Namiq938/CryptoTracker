package com.kryptos.data.io

import com.kryptos.data.models.CryptoModel
import retrofit2.http.GET

interface Api {

    @GET("/v1/bpi/currentprice.json")
    suspend fun getCrypto(): CryptoModel

}
