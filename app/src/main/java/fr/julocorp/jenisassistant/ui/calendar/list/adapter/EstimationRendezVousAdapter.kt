package fr.julocorp.jenisassistant.ui.calendar.list.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.infrastructure.inflate
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarRow
import fr.julocorp.jenisassistant.ui.calendar.list.EstimationRow
import fr.julocorp.jenisassistant.ui.calendar.list.RappelRow
import java.time.format.DateTimeFormatter
import java.util.*

class EstimationRendezVousAdapter(
    private val rendezvousEstimationDeleteListener: (
        adapter: RendezVousListAdapter,
        position: Int,
        id: UUID,
    ) -> Unit
) : RendezVousListAdapter.RendezVousAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = RendezVousEstimationViewHolder(
        parent.inflate(R.layout.viewholder_rdv_estimation)
    )

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        calendarRow: CalendarRow,
        position: Int,
        adapter: RendezVousListAdapter
    ) {
        with((holder as EstimationRendezVousAdapter.RendezVousEstimationViewHolder)) {
            val row = calendarRow as EstimationRow
            textEstimationAddress.text = row.address
            textEstimationFullname.text = row.contactFullname
            textEstimationPhone.text = row.contactPhone
            textEstimationEmail.text = row.contactEmail
            textEstimationComment.text = row.contactComment
            textRendezVousTime.text = row.date.format(DateTimeFormatter.ofPattern("HH:mm"))
            deleteRendezVous.setOnClickListener {
                    it.visibility = View.GONE
                    rendezvousEstimationDeleteListener(adapter, position, row.id)
                }
        }
    }

    inner class RendezVousEstimationViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textRendezVousTime : TextView = item.findViewById(R.id.text_rdv_estimation_time)
        val textEstimationAddress : TextView = item.findViewById(R.id.text_estimation_address)
        val textEstimationFullname : TextView = item.findViewById(R.id.text_estimation_contact_fullname)
        val textEstimationPhone : TextView = item.findViewById(R.id.text_estimation_contact_phone)
        val textEstimationEmail : TextView = item.findViewById(R.id.text_estimation_contact_email)
        val textEstimationComment : TextView = item.findViewById(R.id.text_estimation_contact_comment)
        val deleteRendezVous: ImageView = item.findViewById(R.id.delete_rdv_estimation)
    }
}