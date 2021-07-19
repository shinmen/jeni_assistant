package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.ColumnInfo
import fr.julocorp.jenisassistant.domain.mandatVente.AmenagementType

data class AmenagementEmbed(
    @ColumnInfo(name = "amenagement_label") val amenagementLabel: String,
    @ColumnInfo(name = "amenagement_type") val amenagementType: AmenagementType
)