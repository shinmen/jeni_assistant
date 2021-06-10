package fr.julocorp.jenisassistant.ui.calendar.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.GenericActionState
import fr.julocorp.jenisassistant.domain.Loading
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.useCase.ScheduleRappel
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RappelViewModel @Inject constructor(
    private val useCase: ScheduleRappel,
    private val coroutineContextProvider: CoroutineContextProvider
    ) : ViewModel() {
    private val mutableScheduleResult = MutableLiveData<GenericActionState>()

    val scheduleRappelResult: LiveData<GenericActionState>
        get() = mutableScheduleResult

    fun schedule(rappel: Rappel) {
        viewModelScope.launch(coroutineContextProvider.main) {
            mutableScheduleResult.postValue(Loading)
            val stateResult = useCase.handle(rappel)
            mutableScheduleResult.postValue(stateResult)
        }
    }
}