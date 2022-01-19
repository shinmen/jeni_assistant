package fr.julocorp.jenisassistant.ui.mandatVente.propriete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete.Companion.CARACTERISTIQUE_PROPRIETE_SURFACE_INTERIEURE_LABEL
import fr.julocorp.jenisassistant.domain.mandatVente.RequiredCaracteristiquePropriete
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import fr.julocorp.jenisassistant.domain.mandatVente.useCase.FindProprieteOfEstimation
import fr.julocorp.jenisassistant.domain.mandatVente.useCase.FindRequiredProprieteCaracteristiques
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import fr.julocorp.jenisassistant.infrastructure.findByLabel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ProprieteViewModel @Inject constructor(
    private val findProprieteOfEstimation: FindProprieteOfEstimation,
    private val findRequiredProprieteCaracteristiques: FindRequiredProprieteCaracteristiques,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {
    private val mutablePropriete = MutableLiveData<ActionState<Propriete>>(Loading())
    private val mutableRequiredCaracteristiquesFilled = MutableLiveData<ActionState<List<Caracteristique<*>>>>()

    val propriete: LiveData<ActionState<Propriete>>
        get() = mutablePropriete

    val requiredCaracteristiquesFilled: LiveData<ActionState<List<Caracteristique<*>>>>
        get() = mutableRequiredCaracteristiquesFilled

    fun findPropriete(proprieteId: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            val proprieteState = findProprieteOfEstimation.handle(proprieteId)
//            if (proprieteState is Success) {
//                val caracteristiques = proprieteState.result.caracteristiques
//                mutableRequiredCaracteristiquesFilled.value?.let { requiredCaracteristiquesFilled ->
//                    caracteristiques.findByLabel(CARACTERISTIQUE_PROPRIETE_SURFACE_INTERIEURE_LABEL)?.let {
//                        requiredCaracteristiquesFilled.replace(
//                            RequiredCaracteristiquePropriete.PROPRIETE_NATURE.label, false, true
//                        )
//                    }
//                    caracteristiques.findByLabel(CARACTERISTIQUE_PROPRIETE_SURFACE_INTERIEURE_LABEL)?.let {
//                        requiredCaracteristiquesFilled.replace(
//                            RequiredCaracteristiquePropriete.PROPRIETE_NATURE.label, false, true
//                        )
//                    }
//                    mutableRequiredCaracteristiquesFilled.postValue(requiredCaracteristiquesFilled)
//                }
//            }
            mutablePropriete.postValue(proprieteState)
        }
    }

    fun findRequiredCaracteristiques(proprieteId: UUID) {
        viewModelScope.launch(coroutineContextProvider.main) {
            mutableRequiredCaracteristiquesFilled.postValue(findRequiredProprieteCaracteristiques.handle(proprieteId))
        }
    }
}