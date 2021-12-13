package com.kryptos.common.extensions

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun String.getUnixTime(): Long {
    return try {
        val utcFormat: DateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss Z")
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        utcFormat.parse(this)!!.time
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}

@SuppressLint("SimpleDateFormat")
fun Long.getTimeFromUnitTime(): String {
    return try {
        val dateFormatter: DateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm:ss Z")
        val date = Date(this)
        dateFormatter.timeZone = TimeZone.getTimeZone("UTC")
        dateFormatter.format(date)
    } catch (e: Exception) {
        ""
    }
}