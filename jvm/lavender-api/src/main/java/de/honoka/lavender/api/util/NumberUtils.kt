package de.honoka.lavender.api.util

import java.text.SimpleDateFormat
import java.util.*

fun Int.toStringWithUnit(): String {
    if(this < 10000) return this.toString()
    val numStr = String.format("%.1f", this / 10000.0)
    return numStr.split(".").let {
        if(it[1] == "0") "${it[0]}万" else "${numStr}万"
    }
}

fun Int.toDurationString(): String {
    if(this < 3600) return String.format("%02d:%02d", this / 60, this % 60)
    return String.format("%02d:%02d:%02d", this / 3600, this % 3600 / 60, this % 3600 % 60)
}

fun Long.toDateOrTimeDistanceString(): String {
    return when(val distance = (System.currentTimeMillis() / 1000 - this)) {
        in 0 until 60 -> "刚刚"
        in 60 until 3600 -> "${distance / 60}分钟前"
        in 3600 until 24 * 60 * 60 -> "${distance / 3600}小时前"
        in 24 * 60 * 60 until 10 * 24 * 60 * 60 -> "${distance / (24 * 60 * 60)}天前"
        else -> {
            val dateTime = Date(this * 1000)
            val year = Calendar.getInstance().run {
                time = dateTime
                get(Calendar.YEAR)
            }
            val nowYear = Calendar.getInstance().get(Calendar.YEAR)
            val datePattern = if(year == nowYear) "MM月dd日" else "yyyy年MM月dd日"
            SimpleDateFormat(datePattern).format(dateTime)
        }
    }
}