package fr.julocorp.jenisassistant.ui.mandatVente.estimation

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import fr.julocorp.jenisassistant.R
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.infrastructure.inflate


class ProprieteAdapter(private var propriete: Propriete? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setPropriete(propriete: Propriete) {
        this.propriete = propriete
        notifyItemInserted(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ProprieteViewHolder(
            parent.inflate(
                R.layout.viewholder_estimation_propriete
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        with(holder as ProprieteViewHolder) {
            val context = itemView.context
            val propriete = propriete?.run {
                textProprieteAddresse.text = adresse.toString()
                groupSurfaceInterieure.visibility = GONE
                groupSurfaceExterieure.visibility = GONE
                groupNature.visibility = GONE

                caracteristiques.firstOrNull { it.label == Propriete.CARACTERISTIQUE_PROPRIETE_SURFACE_INTERIEURE_LABEL }
                    ?.let {
                        textProprieteSurfaceInterieur.text =
                            context.resources.getString(R.string.value_m2, it.valeur.toString())
                        groupSurfaceInterieure.visibility = VISIBLE
                    }
                caracteristiques.firstOrNull { it.label == Propriete.CARACTERISTIQUE_PROPRIETE_SURFACE_EXTERIEURE_LABEL }
                    ?.let {
                        textProprieteSurfaceExterieure.text =
                            context.resources.getString(R.string.value_m2, it.valeur.toString())
                        groupSurfaceExterieure.visibility = VISIBLE
                    }
                caracteristiques.firstOrNull { it.label == Propriete.CARACTERISTIQUE_PROPRIETE_NATURE_LABEL }
                    ?.let { caracteristique ->
                        when (true) {
                            caracteristique.valeurEquals("Maison") -> {
                                setIconProprieteNature(
                                    imageProprieteNature,
                                    context,
                                    R.drawable.ic_home,
                                )
                            }
                            caracteristique.valeurEquals("Appartement") -> {
                                setIconProprieteNature(
                                    imageProprieteNature,
                                    context,
                                    R.drawable.ic_buildings,
                                )
                            }
                            else -> { }
                        }
                        textProprieteNature.text = caracteristique.valeur.toString()
                        groupNature.visibility = VISIBLE
                    }
            }
        }

    override fun getItemCount(): Int = if (propriete == null) {
        0
    } else {
        1
    }

    private fun setIconProprieteNature(
        imageProprieteNature: ImageView,
        context: Context,
        icon: Int
    ) {
        imageProprieteNature.setImageDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                icon,
                context.theme
            )
        )
    }

    inner class ProprieteViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val textProprieteAddresse: TextView = item.findViewById(R.id.text_propriete_addresse)
        val textProprieteSurfaceInterieur: TextView =
            item.findViewById(R.id.text_propriete_surface_interieur)
        val groupSurfaceInterieure: Group = item.findViewById(R.id.group_surface_interieure)
        val textProprieteSurfaceExterieure: TextView =
            item.findViewById(R.id.text_propriete_surface_exterieure)
        val groupSurfaceExterieure: Group = item.findViewById(R.id.group_exterieur)
        val textProprieteNature: TextView = item.findViewById(R.id.text_propriete_nature)
        val imageProprieteNature: ImageView = item.findViewById(R.id.icon_propriete_nature)
        val groupNature: Group = item.findViewById(R.id.group_propriete_nature)
    }
}