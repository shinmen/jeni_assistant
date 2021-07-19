package fr.julocorp.jenisassistant.ui.mandatVente.estimation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.domain.mandatVente.useCase.FindProprieteOfEstimation
import fr.julocorp.jenisassistant.domain.mandatVente.useCase.FindRendezVousEstimationWithProprietaire
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class EstimationViewModel @Inject constructor(
    private val findRendezVousEstimationWithProprietaire: FindRendezVousEstimationWithProprietaire,
    private val findProprieteOfEstimation: FindProprieteOfEstimation,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {
    private val mutablePropriete =  MutableLiveData<ActionState<Propriete>>(Loading())
    private val mutableRendezVousEstimationWithProprietaire =
        MutableLiveData<ActionState<RendezVousEstimation>>(Loading())

    val rendezVousEstimationWithProprietaire: LiveData<ActionState<RendezVousEstimation>>
        get() = mutableRendezVousEstimationWithProprietaire

    val propriete: LiveData<ActionState<Propriete>>
        get() = mutablePropriete

    fun findRendezVousEstimation(rendezVousId: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            val state = findRendezVousEstimationWithProprietaire.handle(rendezVousId)
            if (state is Success) {
                findProprieteForEstimation(state.result.proprieteId)
            }
            mutableRendezVousEstimationWithProprietaire.postValue(state)
        }
    }

    private fun findProprieteForEstimation(proprieteId: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            mutablePropriete.postValue(findProprieteOfEstimation.handle(proprieteId))
        }
    }
}