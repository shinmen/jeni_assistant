package fr.julocorp.jenisassistant.ui.calendar.schedule

import androidx.lifecycle.*
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.calendar.exception.InvalidInput
import fr.julocorp.jenisassistant.domain.calendar.useCase.ScheduleCalendarEstimation
import fr.julocorp.jenisassistant.domain.calendar.useCase.SearchFullAddressPropositionsWithPartialAddress
import fr.julocorp.jenisassistant.domain.common.*
import fr.julocorp.jenisassistant.domain.mandatVente.AdresseBien
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.domain.prospection.AdresseProspect
import fr.julocorp.jenisassistant.domain.prospection.Contact
import fr.julocorp.jenisassistant.domain.prospection.ContactOrigin
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CalendarEstimationViewModel @Inject constructor(
    private val searchFullAddressPropositionsWithPartialAddress: SearchFullAddressPropositionsWithPartialAddress,
    private val scheduleCalendarEstimation: ScheduleCalendarEstimation,
    private val coroutineContextProvider: CoroutineContextProvider,
) : ViewModel() {
    private val mutableAddressePropositions = MutableLiveData<ActionState<List<FullAddress>>>()
    private val mutableRendezVousEstimation = MutableLiveData(RendezVousEstimationInput())
    private val mutableRendezVousEstimationSave = MediatorLiveData<ActionState<Boolean>>()

    val addressesPropositions: LiveData<ActionState<List<FullAddress>>>
        get() = mutableAddressePropositions

    val rendezVousEstimationInput: LiveData<RendezVousEstimationInput>
        get() = mutableRendezVousEstimation

    val rendezVousEstimationSave: LiveData<ActionState<Boolean>>
        get() = mutableRendezVousEstimationSave

    fun findAddress(search: String) {
        viewModelScope.launch(coroutineContextProvider.main) {
            mutableAddressePropositions.postValue(Loading())
            try {
                val listAddresses = searchFullAddressPropositionsWithPartialAddress.handle(search)
                mutableAddressePropositions.postValue(Success(listAddresses))
            } catch (e: Throwable) {
                Failure<List<FullAddress>>(e)
            }
        }
    }


    fun updateRendezvousInput(rendezVousEstimationInput: RendezVousEstimationInput) {
        mutableRendezVousEstimation.postValue(rendezVousEstimationInput)
    }

    fun schedule() {
        mutableRendezVousEstimationSave.addSource(rendezVousEstimationInput) { input ->
            mutableRendezVousEstimationSave.removeSource(rendezVousEstimationInput)
            viewModelScope.launch(coroutineContextProvider.main) {
                try {
                    validateRequiredInput(input)
                    val rendezVousEstimation = RendezVousEstimation(
                        UUID.randomUUID(),
                        input.rendezVousDate!!,
                        input.address!!,
                        Contact(
                            ContactOrigin.INOPINE,
                            input.fullName!!,
                            input.phoneNumber,
                            input.email,
                            input.comment,
                        )
                    )
                    val result = scheduleCalendarEstimation.handle(rendezVousEstimation)
                    mutableRendezVousEstimationSave.postValue(result)
                } catch (e: Throwable) {
                    mutableRendezVousEstimationSave.postValue(Failure(e))
                }
            }
        }
    }

    private fun validateRequiredInput(rendezVousEstimationInput: RendezVousEstimationInput) {
        mutableMapOf<Int, Int>()
            .apply {
                if (rendezVousEstimationInput.address == null) {
                    put(R.string.field_address, R.string.missing_address)
                }
                if (rendezVousEstimationInput.fullName == null) {
                    put(R.string.field_fullname, R.string.required_field)
                }
                if (rendezVousEstimationInput.rendezVousDate == null) {
                    put(R.string.field_rendezvous_date, R.string.required_field)
                }
                if (rendezVousEstimationInput.phoneNumber == null) {
                    put(R.string.field_phone, R.string.required_field)
                }
            }
            .also {
                if (it.isNotEmpty()) {
                    throw InvalidInput(it)
                }
            }
    }
}