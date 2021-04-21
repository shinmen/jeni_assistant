package fr.julocorp.jenisassistant.infrastructure

import android.content.Context
import android.widget.ArrayAdapter
import fr.julocorp.jenisassistant.domain.common.FullAddress

class GeoLocationListAdapter(context: Context, resource: Int): ArrayAdapter<FullAddress>(context, resource) {

    fun proposeNewAddresses(addressePropositions: List<FullAddress>) {
        clear()
        addAll(addressePropositions)
        notifyDataSetChanged()
    }
}