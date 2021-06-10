package fr.julocorp.jenisassistant.ui.calendar.list.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.infrastructure.inflate
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarRow

class EstimationVousAdapter : RendezVousListAdapter.RendezVousAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = DayViewHolder(
        parent.inflate(R.layout.viewholder_rdv_estimation)
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        calendarRow: CalendarRow,
        position: Int,
        adapter: RendezVousListAdapter
    ) {
        //holder.itemView.findViewById()
    }

    inner class DayViewHolder(item: View) : RecyclerView.ViewHolder(item) {

    }
}