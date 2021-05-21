package fr.julocorp.jenisassistant.ui.calendar.schedule

import androidx.lifecycle.ViewModel
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.useCase.ScheduleRappel
import javax.inject.Inject

class ReminderViewModel @Inject constructor(private val useCase: ScheduleRappel) : ViewModel() {
    fun schedule(rappel: Rappel) {
        useCase.handle(rappel)
    }
}