package fr.julocorp.jenisassistant.ui.calendar.list.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.infrastructure.inflate
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarRow
import fr.julocorp.jenisassistant.ui.calendar.list.DayRow
import java.time.format.DateTimeFormatter

class DayAdapter : RendezVousListAdapter.RendezVousAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = DayViewHolder(
        parent.inflate(R.layout.viewholder_rdv_day)
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, calendarRow: CalendarRow) {
        (holder as DayViewHolder).textDay.text = (calendarRow as DayRow).day
            .format(DateTimeFormatter.ofPattern("EE d MMM y"))
    }

    inner class DayViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textDay: TextView = item.findViewById(R.id.text_day)
    }
}