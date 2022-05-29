package com.aspire.sawa.repositoires

import android.content.SharedPreferences
import com.aspire.sawa.unitls.Constraints.CHECK_IN_ID
import com.aspire.sawa.unitls.Constraints.CHECK_IN_Time
import javax.inject.Inject

class CheckInRepositories @Inject constructor(
    private val pref: SharedPreferences,
) {

    fun checkIn(id: String, checkInTime: String) =
        pref.edit().putString(CHECK_IN_ID, id).commit() &&
                pref.edit().putString(CHECK_IN_Time, checkInTime).commit()

    fun checkOut() =
        pref.edit().remove(CHECK_IN_ID).commit() && pref.edit().remove(CHECK_IN_Time).commit()

    fun getId(): String? =
        pref.getString(CHECK_IN_ID, null)

    fun getCheckInTime(): String =
        pref.getString(CHECK_IN_Time, null) ?: "00:00:00"
}