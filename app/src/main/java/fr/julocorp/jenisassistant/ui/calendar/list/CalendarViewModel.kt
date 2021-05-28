package fr.julocorp.jenisassistant.ui.calendar.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val rappelRepository: RappelRepository
) : ViewModel() {

    private val mutableCalendarRows = MutableLiveData(listOf<CalendarRow>())

    val calendarRows: LiveData<List<CalendarRow>>
        get() = mutableCalendarRows

    fun fetchCalendarRow() {
        val rappels = viewModelScope.async {
            rappelRepository.findRappels()
        }
        val dateFormatter = SimpleDateFormat("EE d MMM")
        val calendarRows = mutableListOf<CalendarRow>()
        val byDays = listOf<CalendarRow>()
        Calendar.getInstance().time.toInstant()
        calendarRows.add(DayRow(Calendar.getInstance()))
        LocalDateTime.now()

    }
}