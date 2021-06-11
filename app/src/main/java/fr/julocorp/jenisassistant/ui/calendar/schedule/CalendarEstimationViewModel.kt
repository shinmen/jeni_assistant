package fr.julocorp.jenisassistant.ui.calendar.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CalendarEstimationViewModel @Inject constructor(
    private val addresseSearcher: AdresseSearcher,
    private val coroutineContextProvider: CoroutineContextProvider
) : ViewModel() {
    private val mutableAddresses = MutableLiveData<List<FullAddress>>()

    val addressesPropositions: LiveData<List<FullAddress>>
        get() = mutableAddresses

    fun findAddress(
        search: String
    ) {
        viewModelScope.launch(coroutineContextProvider.main) {
            addresseSearcher.findByPartialAddress(search)
                .collect { listAddresses ->
                    mutableAddresses.postValue(listAddresses)
                }
        }
    }
}