package fr.julocorp.jenisassistant.infrastructure.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import fr.julocorp.jenisassistant.domain.common.FullAddress

class AddressConverter {
    private val jsonAdapter = Moshi.Builder().build().adapter(FullAddress::class.java)

    @TypeConverter
    fun fromAddress(address: FullAddress): String = jsonAdapter.toJson(address)
    @TypeConverter
    fun toAddress(adress: String): FullAddress? = jsonAdapter.fromJson(adress)
}