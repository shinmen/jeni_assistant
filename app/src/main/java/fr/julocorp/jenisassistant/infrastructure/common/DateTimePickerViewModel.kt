package fr.julocorp.jenisassistant.infrastructure.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.julocorp.jenisassistant.ui.common.Dummy
import java.util.*
import javax.inject.Inject

class DateTimePickerViewModel @Inject constructor(private val dummy: Dummy) : ViewModel() {
    private val mutableDatetimePicked = MutableLiveData(Calendar.getInstance())

    val dateTimePicked: LiveData<Calendar>
        get() = mutableDatetimePicked

    fun setDate(date: Calendar) {
        dummy.test()
        val dateUpdated = mutableDatetimePicked.value?.apply {
            set(
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH)
            )
        }
        mutableDatetimePicked.postValue(dateUpdated)
    }

    fun setTime(time: Calendar) {
        mutableDatetimePicked.value?.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY))
        mutableDatetimePicked.value?.set(Calendar.MINUTE, time.get(Calendar.MINUTE))

        mutableDatetimePicked.postValue(mutableDatetimePicked.value)
    }

    fun reset() {
        mutableDatetimePicked.postValue(Calendar.getInstance())
    }
}
