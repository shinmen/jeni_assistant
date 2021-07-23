package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.TypeConverter
import fr.julocorp.jenisassistant.domain.mandatVente.AmenagementType

class CaracteristiqueValueTypeConverter {
    @TypeConverter
    fun fromValueType(valueType: CaracteristiqueValueType): String = valueType.name
    @TypeConverter
    fun toValueType(valueType: String): CaracteristiqueValueType = CaracteristiqueValueType.valueOf(valueType)
}