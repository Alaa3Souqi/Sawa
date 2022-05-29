package com.aspire.sawa.repositoires

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TimeRepositories @Inject constructor() {

    fun getCurrentTime(): String = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH).format(Date())

    fun getTimeStringFromSeconds(seconds: Int): String {

        val hours: Int = seconds / 3600
        val minutes: Int = seconds % 3600 / 60
        val second: Int = seconds % 60

        return makeTimeString(hours, minutes, second)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String =
        String.format("%02d:%02d:%02d", hour, min, sec)

    fun differenceBetweenTwoTimes(time1: String, time2: String): Long {
        val format = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)

        format.parse(time1)?.time?.let { currentTime ->
            format.parse(time2)?.time?.let { checkInTime ->
                return (currentTime - checkInTime) / 1000
            }
        }
        return -1
    }

}