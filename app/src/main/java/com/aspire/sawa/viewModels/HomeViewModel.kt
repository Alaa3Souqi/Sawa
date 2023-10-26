package com.aspire.sawa.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspire.sawa.models.CheckedInPlace
import com.aspire.sawa.repositoires.CheckInRepositories
import com.aspire.sawa.repositoires.TimeRepositories
import com.aspire.sawa.unitls.ScreenState
import com.aspire.sawa.unitls.SingleLiveEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val timeRepo: TimeRepositories,
    private val checkInRepo: CheckInRepositories
) : ViewModel() {

    private val _timer = SingleLiveEvent<String>()
    val timer: LiveData<String>
        get() = _timer

    private val _checkedInPlace = SingleLiveEvent<CheckedInPlace>()
    val checkedInPlace: LiveData<CheckedInPlace>
        get() = _checkedInPlace

    private val _checkInValidate = SingleLiveEvent<ScreenState>()
    val checkInValidate: LiveData<ScreenState>
        get() = _checkInValidate

    private var isComplete = false

    fun getCheckedInPlace() {
        checkInRepo.getId()?.let { id ->
            _checkedInPlace.postValue(CheckedInPlace(id, timeRepo.getCurrentTime()))
            isComplete = false
        }
    }

    private var job: Job? = null

    fun refreshTimer() {
        job = viewModelScope.launch {
            while (!isComplete && isActive) {

                val seconds = timeRepo.differenceBetweenTwoTimes(
                    timeRepo.getCurrentTime(),
                    checkInRepo.getCheckInTime()
                )

                val timeStringFromDouble = timeRepo.getTimeStringFromSeconds(seconds.toInt())

                _timer.postValue(timeStringFromDouble)

                Log.d("TAG", "refreshTimer: $timeStringFromDouble")

                delay(1000)

                checkedInPlace.value

            }
        }
    }

    fun checkIn(id: String) {
        _checkInValidate.postValue(checkInRepo.checkIn(id, timeRepo.getCurrentTime()))
    }

    fun checkOut() {
        isComplete = true
        checkInRepo.checkOut()
        cancelJob()
    }

    fun cancelJob() {
        job?.cancel()
    }


}