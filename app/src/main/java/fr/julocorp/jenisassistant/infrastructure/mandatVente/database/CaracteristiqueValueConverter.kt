package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

class CaracteristiqueValueConverter() {
    private val valueJsonAdapter = Moshi.Builder().build().adapter(CaracteristiqueValue::class.java)

    @TypeConverter
    fun fromCaracteristiqueValue(caracteristiqueValue: CaracteristiqueValue): String =
        valueJsonAdapter.toJson(caracteristiqueValue)


    @TypeConverter
    fun toCaracteristiqueValue(caracteristiqueValue: String): CaracteristiqueValue? =
        valueJsonAdapter.fromJson(caracteristiqueValue)

}
