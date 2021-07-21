package fr.julocorp.jenisassistant.ui.calendar.list

import java.time.LocalDateTime

interface DateTimeOfRow {
    fun getDateTime(): LocalDateTime
}