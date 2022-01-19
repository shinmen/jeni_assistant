package fr.julocorp.jenisassistant.infrastructure.mandatVente.repository

import com.squareup.moshi.Moshi
import fr.julocorp.jenisassistant.domain.mandatVente.Amenagement
import fr.julocorp.jenisassistant.domain.mandatVente.AmenagementType
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.*
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.CaracteristiqueDefinitionEntity
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.CaracteristiqueValueType
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.CaracteristiqueWithDefinitionEntity

class CaracteristiqueMapper {
    private val listJsonAdapter by lazy {
        Moshi.Builder().build().adapter<List<String>>(List::class.java)
    }

    private fun toDomain(caracteristiqueWithDefinition: CaracteristiqueWithDefinitionEntity): Caracteristique<*> =
        when (caracteristiqueWithDefinition.caracteristique.valeur.type) {
            CaracteristiqueValueType.TEXT -> CaracteristiqueText(
                caracteristiqueWithDefinition.caracteristique.id,
                caracteristiqueWithDefinition.caracteristique.label,
                caracteristiqueWithDefinition.caracteristique.valeur.value,
                mapDefinition(caracteristiqueWithDefinition.definition)
            )
            CaracteristiqueValueType.MULTIPLE -> CaracteristiqueMultiple(
                caracteristiqueWithDefinition.caracteristique.id,
                caracteristiqueWithDefinition.caracteristique.label,
                listJsonAdapter.fromJson(caracteristiqueWithDefinition.caracteristique.valeur.value) ?: listOf(),
                mapDefinition(caracteristiqueWithDefinition.definition)
            )
            CaracteristiqueValueType.INT -> CaracteristiqueInt(
                caracteristiqueWithDefinition.caracteristique.id,
                caracteristiqueWithDefinition.caracteristique.label,
                caracteristiqueWithDefinition.caracteristique.valeur.value.toInt(),
                mapDefinition(caracteristiqueWithDefinition.definition)
            )
            CaracteristiqueValueType.FLOAT -> CaracteristiqueFloat(
                caracteristiqueWithDefinition.caracteristique.id,
                caracteristiqueWithDefinition.caracteristique.label,
                caracteristiqueWithDefinition.caracteristique.valeur.value.toFloat(),
                mapDefinition(caracteristiqueWithDefinition.definition)
            )
            CaracteristiqueValueType.BOOL -> CaracteristiqueBool(
                caracteristiqueWithDefinition.caracteristique.id,
                caracteristiqueWithDefinition.caracteristique.label,
                caracteristiqueWithDefinition.caracteristique.valeur.value.toBoolean(),
                mapDefinition(caracteristiqueWithDefinition.definition)
            )
        }

    fun mapToPropriete(caracteristiquesWithDefinition: List<CaracteristiqueWithDefinitionEntity>): List<Caracteristique<*>> {
        return caracteristiquesWithDefinition
            .filter { it.caracteristique.amenagement.amenagementType == AmenagementType.PROPRIETE }
            .map { toDomain(it) }
    }

    fun mapToAmenagement(caracteristiquesWithDefinition: List<CaracteristiqueWithDefinitionEntity>): List<Amenagement> {
        return caracteristiquesWithDefinition
            .filter {it.caracteristique.amenagement.amenagementType !== AmenagementType.PROPRIETE}
            .groupBy { it.caracteristique.amenagement.amenagementLabel }
            .map { groupedCaracteristiques ->
                Amenagement(
                    groupedCaracteristiques.value.first().caracteristique.amenagement.amenagementLabel,
                    groupedCaracteristiques.value.first().caracteristique.amenagement.amenagementType,
                    groupedCaracteristiques.value.map { caracteristique -> toDomain(caracteristique) }
                )
            }
    }

    private fun mapDefinition(caracteristiqueDefinition: CaracteristiqueDefinitionEntity) = Definition(
        caracteristiqueDefinition.label,
        caracteristiqueDefinition.validationRules,
        caracteristiqueDefinition.availableValues,
        caracteristiqueDefinition.suffix
    )
}
