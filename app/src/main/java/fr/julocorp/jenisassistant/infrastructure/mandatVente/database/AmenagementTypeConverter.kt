package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.TypeConverter
import fr.julocorp.jenisassistant.domain.mandatVente.AmenagementType

class AmenagementTypeConverter {
    @TypeConverter
    fun fromAmenagementType(amenagementType: AmenagementType): String = amenagementType.name
    @TypeConverter
    fun toAmenagementType(amenagementType: String): AmenagementType = AmenagementType.valueOf(amenagementType)
}