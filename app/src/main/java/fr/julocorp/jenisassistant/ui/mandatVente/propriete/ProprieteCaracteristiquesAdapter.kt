package fr.julocorp.jenisassistant.ui.mandatVente.propriete

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import fr.julocorp.jenisassistant.infrastructure.inflate

class ProprieteCaracteristiquesAdapter(
    private val caracteristiques: List<Caracteristique<*>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CaracteristiqueViewHolder(parent.inflate(R.layout.viewholder_propriete_caracteristique_list))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = with(holder as CaracteristiqueViewHolder) {
        textLabelCaracteristique.text = "${caracteristiques[position].label.replaceFirstChar(Char::uppercase)}:"
        textValueCaracteristique.text = caracteristiques[position].valeur.toString()
    }

    override fun getItemCount(): Int = caracteristiques.size

    inner class CaracteristiqueViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textLabelCaracteristique: TextView = item.findViewById(R.id.caracteristique_label)
        val textValueCaracteristique: TextView = item.findViewById(R.id.caracteristique_value)
    }
}