package fr.julocorp.jenisassistant.ui.calendar.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private val mutableCalendarRows = MutableLiveData(listOf<CalendarRow>())

    val calendarRows: LiveData<List<CalendarRow>>
        get() = mutableCalendarRows

    fun fetchCalendarRow() {
        viewModelScope.launch(coroutineContextProvider.IO) {
            val calendarRowsGroupedByDate = TreeMap<LocalDate, MutableList<CalendarRow>>()
            val deferredRappels = async { rappelRepository.findRappels() }

            calendarRowsGroupedByDate.putIfAbsent(
                LocalDate.now(),
                mutableListOf()
            )

            deferredRappels.await().also { rappels ->
                rappels
                    .map { rappel ->
                        val row = RappelRow(rappel.id, rappel.sujet, rappel.rappelDate)
                        calendarRowsGroupedByDate.putIfAbsent(
                            rappel.rappelDate.toLocalDate(),
                            mutableListOf()
                        )
                        calendarRowsGroupedByDate[rappel.rappelDate.toLocalDate()]?.add(row)
                    }
            }


            mutableCalendarRows.postValue(
                calendarRowsGroupedByDate
                    .map { rowsGroupByDate ->
                        val isToday = LocalDate.now().equals(rowsGroupByDate.key)
                        rowsGroupByDate.value.add(0, DayRow(rowsGroupByDate.key, isToday))
                        rowsGroupByDate.value.add(SeparatorRow)
                        rowsGroupByDate
                    }
                    .flatMap { rowsGroupByDate -> rowsGroupByDate.value.toList() }
            )
        }
    }
}