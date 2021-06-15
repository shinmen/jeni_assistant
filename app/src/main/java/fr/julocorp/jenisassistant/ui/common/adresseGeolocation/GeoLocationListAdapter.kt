package fr.julocorp.jenisassistant.ui.common.adresseGeolocation

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import fr.julocorp.jenisassistant.domain.common.FullAddress

class GeoLocationListAdapter(
    context: Context,
    resource: Int,
    private val addresseFilter: Filter = AddresseFilter()
) : ArrayAdapter<FullAddress>(context, resource) {

    fun proposeNewAddresses(addressePropositions: List<FullAddress>) {
        clear()
        addAll(addressePropositions)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter = addresseFilter
}

class AddresseFilter : Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults  = FilterResults()

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
    }
}

