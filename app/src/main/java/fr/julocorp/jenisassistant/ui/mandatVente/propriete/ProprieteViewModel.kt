package fr.julocorp.jenisassistant.ui.mandatVente.propriete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.domain.mandatVente.useCase.FindProprieteOfEstimation
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ProprieteViewModel @Inject constructor(
    private val findProprieteOfEstimation: FindProprieteOfEstimation,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {
    private val mutablePropriete = MutableLiveData<ActionState<Propriete>>(Loading())
    val propriete: LiveData<ActionState<Propriete>>
        get() = mutablePropriete

    fun findPropriete(proprieteId: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            mutablePropriete.postValue(findProprieteOfEstimation.handle(proprieteId))
        }
    }
}