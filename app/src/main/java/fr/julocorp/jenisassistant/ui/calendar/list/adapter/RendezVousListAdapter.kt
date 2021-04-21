package fr.julocorp.jenisassistant.ui.calendar.list.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.infrastructure.ViewType
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarRow
import fr.julocorp.jenisassistant.ui.calendar.list.SeparatorRow

class RendezVousListAdapter(private val adapters: Map<ViewType, RendezAdapter>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        adapters.getOrDefault(viewType, SeparatorAdapter()).onCreateViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        adapters.getOrDefault(getItemViewType(position), SeparatorAdapter()).onBindViewHolder(holder, SeparatorRow)

    override fun getItemCount(): Int = 0

    interface RendezAdapter {
        fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        fun onBindViewHolder(holder: RecyclerView.ViewHolder, calendarRow: CalendarRow)
    }
}