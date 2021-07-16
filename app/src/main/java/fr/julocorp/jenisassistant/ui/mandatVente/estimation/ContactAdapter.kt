package fr.julocorp.jenisassistant.ui.mandatVente.estimation

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.prospection.Contact
import fr.julocorp.jenisassistant.infrastructure.inflate


class ContactAdapter(private val contacts: MutableList<Contact>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun addContact(contact: Contact) {
        contacts.add(contact)
        notifyItemInserted(contacts.size)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ContactViewHolder(parent.inflate(
        R.layout.viewholder_estimation_contact_list))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = with(holder as ContactViewHolder) {
        textEstimationFullname.text = contacts[position].fullname
        textEstimationPhone.text = contacts[position].phoneNumber
        textEstimationEmail.text = contacts[position].email
        textEstimationComment.text = contacts[position].commentaire
    }

    override fun getItemCount(): Int = contacts.size

    inner class ContactViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val textEstimationFullname: TextView = item.findViewById(R.id.text_contact_fullname)
        val textEstimationPhone: TextView = item.findViewById(R.id.text_contact_phone)
        val textEstimationEmail: TextView = item.findViewById(R.id.text_contact_email)
        val textEstimationComment: TextView = item.findViewById(R.id.text_contact_comment)
        val cardProprietaire: CardView = item.findViewById(R.id.card_contact)
    }
}