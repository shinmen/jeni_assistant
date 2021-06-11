package fr.julocorp.jenisassistant.ui.common.adresseGeolocation

import android.content.Context
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DiffUtil
import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.ui.calendar.list.adapter.CalendarRowDiffCallback

class GeoLocationListAdapter(context: Context, resource: Int): ArrayAdapter<FullAddress>(context, resource) {
    fun proposeNewAddresses(addressePropositions: List<FullAddress>) {
        clear()
        addAll(addressePropositions)
        notifyDataSetChanged()
    }
}