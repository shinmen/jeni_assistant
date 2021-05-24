package fr.julocorp.jenisassistant.ui.calendar.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.useCase.ScheduleRappel
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReminderViewModel @Inject constructor(
    private val useCase: ScheduleRappel,
    private val coroutineContext: CoroutineContextProvider
    ) : ViewModel() {
    fun schedule(rappel: Rappel) {
        viewModelScope.launch(coroutineContext.IO) {
            useCase.handle(rappel)
        }
    }
}