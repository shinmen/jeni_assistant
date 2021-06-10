package fr.julocorp.jenisassistant.ui.calendar.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.useCase.EndRappel
import fr.julocorp.jenisassistant.domain.common.useCase.ListEvents
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val listEventsUseCase: ListEvents,
    private val endRappelUseCase: EndRappel,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private val mutableCalendarRows = MutableLiveData(listOf<CalendarRow>())

    val calendarRows: LiveData<List<CalendarRow>>
        get() = mutableCalendarRows

    fun fetchCalendarRow() {
        viewModelScope.launch(coroutineContextProvider.main) {
            when(val result = listEventsUseCase.handle()) {
                is Success -> mutableCalendarRows.postValue(result.result as List<CalendarRow>)
                else -> {}
            }
        }
    }

    fun markRappelAsDone(id: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            endRappelUseCase.handle(id)
            fetchCalendarRow()
        }
    }
}