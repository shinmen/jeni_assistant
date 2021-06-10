package fr.julocorp.jenisassistant.ui.common.datetimePicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

class DateTimePickerViewModel @Inject constructor() : ViewModel() {
    private val mutableDatetimePicked = MutableLiveData(LocalDateTime.now())

    val dateTimePicked: LiveData<LocalDateTime>
        get() = mutableDatetimePicked

    fun setDate(date: LocalDate) {
        val dateUpdated = mutableDatetimePicked.value?.let {
            LocalDateTime.of(date, it.toLocalTime())
        }
        mutableDatetimePicked.postValue(dateUpdated)
    }

    fun setTime(time: LocalTime) {
        val timeUpdated = mutableDatetimePicked.value?.let {
            LocalDateTime.of(it.toLocalDate(), time)
        }
        mutableDatetimePicked.postValue(timeUpdated)
    }
}
