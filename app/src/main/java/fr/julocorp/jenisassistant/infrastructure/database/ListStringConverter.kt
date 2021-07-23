package fr.julocorp.jenisassistant.infrastructure.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

class ListStringConverter {
    private val jsonAdapter = Moshi.Builder().build().adapter(List::class.java)

    @TypeConverter
    fun fromList(list: List<String>): String = jsonAdapter.toJson(list)
    @TypeConverter
    fun toString(list: String): List<String> = jsonAdapter.fromJson(list) as List<String>
}