package fr.julocorp.jenisassistant.ui.mandatVente.propriete

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        with(holder as CaracteristiqueViewHolder) {
            val caracteristique = caracteristiques[position]
            textLabelCaracteristique.text = itemView.resources.getString(
                R.string.label_with,
                caracteristique.label.replaceFirstChar(Char::uppercase)
            )
            textValueCaracteristique.text = if (caracteristique.definition.suffix !== null) {
                itemView.resources.getString(
                    R.string.value_with_suffix,
                    caracteristique.valeur.toString(),
                    caracteristique.definition.suffix
                )
            } else {
                caracteristique.valeur.toString()
            }
        }

    override fun getItemCount(): Int = caracteristiques.size

    inner class CaracteristiqueViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textLabelCaracteristique: TextView = item.findViewById(R.id.caracteristique_label)
        val textValueCaracteristique: TextView = item.findViewById(R.id.caracteristique_value)
    }
}