package fr.julocorp.jenisassistant.ui.calendar.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.calendar.useCase.ScheduleRappel
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class RappelViewModel @Inject constructor(
    private val useCase: ScheduleRappel,
    private val coroutineContextProvider: CoroutineContextProvider
    ) : ViewModel() {
    private val mutableScheduleResult = MutableLiveData<ActionState<Boolean>>()

    val scheduleRappelResult: LiveData<ActionState<Boolean>>
        get() = mutableScheduleResult

    fun schedule(rappel: Rappel) {
        viewModelScope.launch(coroutineContextProvider.main) {
            mutableScheduleResult.postValue(Loading())
            mutableScheduleResult.postValue(useCase.handle(rappel))
        }
    }
}