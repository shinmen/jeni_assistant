package fr.julocorp.jenisassistant.ui.calendar.list.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.infrastructure.inflate
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarRow
import fr.julocorp.jenisassistant.ui.calendar.list.RappelRow
import java.time.format.DateTimeFormatter
import java.util.*

class RappelAdapter(
    private val listener : (
        adapter: RendezVousListAdapter,
        position: Int,
        id: UUID,
    ) -> Unit
) : RendezVousListAdapter.RendezVousAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = RappelViewHolder(
        parent.inflate(R.layout.viewholder_rdv_rappel)
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        calendarRow: CalendarRow,
        position: Int,
        adapter: RendezVousListAdapter
    ) {
        with((holder as RappelViewHolder)) {
            val row = calendarRow as RappelRow
            textRappel.text = row.sujet
            textRappelTime.text = row.date.format(DateTimeFormatter.ofPattern("HH:mm"))
            deleteRappel.setOnClickListener {
                it.visibility = View.GONE
                listener(adapter, position, row.id)
            }
        }
    }

    inner class RappelViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textRappelTime : TextView = item.findViewById(R.id.text_rappel_time)
        val textRappel : TextView = item.findViewById(R.id.text_rappel)
        val deleteRappel: ImageView = item.findViewById(R.id.delete_rappel)
    }
}