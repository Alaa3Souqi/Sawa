package com.aspire.sawa.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspire.sawa.models.CheckedInPlace
import com.aspire.sawa.repositoires.CheckInRepositories
import com.aspire.sawa.repositoires.TimeRepositories
import com.aspire.sawa.unitls.SingleLiveEvent
import kotlinx.coroutines.delay
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

    private var isComplete = false

    fun getCheckedInPlace() {
        checkInRepo.getId()?.let { id ->
            _checkedInPlace.postValue(CheckedInPlace(id, timeRepo.getCurrentTime()))
        }
    }

    fun refreshTimer() {
        viewModelScope.launch {
            while (!isComplete) {

                val seconds = timeRepo.differenceBetweenTwoTimes(
                    timeRepo.getCurrentTime(),
                    checkInRepo.getCheckInTime()
                )

                val timeStringFromDouble = timeRepo.getTimeStringFromSeconds(seconds.toInt())

                _timer.postValue(timeStringFromDouble)

                delay(1000)
            }
        }
    }

    fun checkIn(id: String) {
        checkInRepo.checkIn(id, timeRepo.getCurrentTime())
        isComplete = false
    }

    fun checkOut() {
        isComplete = true
        checkInRepo.checkOut()
    }

}