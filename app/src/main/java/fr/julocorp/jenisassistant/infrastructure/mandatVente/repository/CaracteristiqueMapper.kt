package fr.julocorp.jenisassistant.infrastructure.mandatVente.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import fr.julocorp.jenisassistant.domain.mandatVente.Amenagement
import fr.julocorp.jenisassistant.domain.mandatVente.AmenagementType
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.*
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.CaracteristiqueEntity
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.CaracteristiqueValueType

class CaracteristiqueMapper {
    private val listJsonAdapter by lazy {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        //Moshi.Builder().build().adapter<List<String>>(type)
        Moshi.Builder().build().adapter<List<String>>(List::class.java)
    }

    private fun toDomain(caracteristique: CaracteristiqueEntity): Caracteristique<*> =
        when (caracteristique.valeur.type) {
            CaracteristiqueValueType.TEXT -> CaracteristiqueText(
                caracteristique.id,
                caracteristique.label,
                caracteristique.valeur.value
            )
            CaracteristiqueValueType.MULTIPLE -> CaracteristiqueMultiple(
                caracteristique.id,
                caracteristique.label,
                listJsonAdapter.fromJson(caracteristique.valeur.value) ?: listOf()
            )
            CaracteristiqueValueType.INT -> CaracteristiqueInt(
                caracteristique.id,
                caracteristique.label,
                caracteristique.valeur.value.toInt()
            )
            CaracteristiqueValueType.FLOAT -> CaracteristiqueFloat(
                caracteristique.id,
                caracteristique.label,
                caracteristique.valeur.value.toFloat()
            )
        }


    fun mapToPropriete(caracteristiques: List<CaracteristiqueEntity>): List<Caracteristique<*>> {
        return caracteristiques
            .filter { it.amenagement.amenagementType == AmenagementType.PROPRIETE }
            .map { toDomain(it) }
    }

    fun mapToAmenagement(caracteristiques: List<CaracteristiqueEntity>): List<Amenagement> {
        return caracteristiques
            .filter {it.amenagement.amenagementType !== AmenagementType.PROPRIETE}
            .groupBy { it.amenagement.amenagementLabel }
            .map { groupedCaracteristiques ->
                Amenagement(
                    groupedCaracteristiques.value.first().amenagement.amenagementLabel,
                    groupedCaracteristiques.value.first().amenagement.amenagementType,
                    groupedCaracteristiques.value.map { caracteristique -> toDomain(caracteristique) }
                )
            }
    }
}