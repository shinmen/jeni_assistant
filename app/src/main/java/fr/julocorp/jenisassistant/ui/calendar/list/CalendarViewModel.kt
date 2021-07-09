package fr.julocorp.jenisassistant.ui.calendar.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRappel
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRendezVousEstimation
import fr.julocorp.jenisassistant.domain.calendar.useCase.ListEvents
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.util.*
import javax.inject.Inject

class CalendarViewModel @Inject constructor(
    private val listEventsUseCase: ListEvents,
    private val endRappelUseCase: EndRappel,
    private val endRendezVousEstimation: EndRendezVousEstimation,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {

    private val mutableCalendarRows = MutableLiveData<ActionState<List<CalendarRow>>>(Loading())

    val calendarRows: LiveData<ActionState<List<CalendarRow>>>
        get() = mutableCalendarRows

    fun fetchCalendarRow() {
        viewModelScope.launch(coroutineContextProvider.main) {
            mutableCalendarRows.postValue(listEventsUseCase.handle())
        }
    }

    fun markRappelAsDone(id: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            endRappelUseCase.handle(id)
            fetchCalendarRow()
        }
    }

    fun markRendezvousEstimationAsDone(id: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            endRendezVousEstimation.handle(id)
            fetchCalendarRow()
        }
    }
}