package fr.julocorp.jenisassistant.ui.mandatVente.estimation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.domain.mandatVente.useCase.FindRendezVousEstimationWithProprietaire
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class EstimationViewModel @Inject constructor(
    private val findRendezVousEstimationWithProprietaire: FindRendezVousEstimationWithProprietaire,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {
    private val mutableBien =  MutableLiveData<ActionState<Propriete>>(Loading())
    private val mutableRendezVousEstimationWithProprietaire =
        MutableLiveData<ActionState<RendezVousEstimation>>(Loading())

    val rendezVousEstimationWithProprietaire: LiveData<ActionState<RendezVousEstimation>>
        get() = mutableRendezVousEstimationWithProprietaire


    fun findRendezVousEstimation(rendezVousId: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            mutableRendezVousEstimationWithProprietaire.postValue(
                findRendezVousEstimationWithProprietaire.handle(rendezVousId)
            )
        }
    }
}