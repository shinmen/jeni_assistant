package fr.julocorp.jenisassistant.ui.common

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