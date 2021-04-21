package fr.julocorp.jenisassistant.infrastructure.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.infrastructure.repository.ApiGeoGouvAdresseSearcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

//class CalendarEstimationViewModel(private val addresseSearcher : ApiGeoGouvAdresseSearcher, private val coroutineContext: CoroutineContext = Dispatchers.IO) : ViewModel() {
class CalendarEstimationViewModel() : ViewModel() {
    private val mutableAddresses =  MutableLiveData<List<FullAddress>>()

    val addressesPropositions: LiveData<List<FullAddress>>
        get() = mutableAddresses

    fun findAdress(search: String, addresseSearcher : ApiGeoGouvAdresseSearcher, coroutineContext: CoroutineContext = Dispatchers.IO) {
        viewModelScope.launch(coroutineContext) {
            mutableAddresses.postValue(addresseSearcher.findByPartialAddress(search))
        }
    }
}