package fr.julocorp.jenisassistant.ui.calendar.list

import fr.julocorp.jenisassistant.infrastructure.TypeIdentifier
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

sealed class CalendarRow: TypeIdentifier

data class DayRow(val day: LocalDate, val isToday: Boolean): CalendarRow() {
    companion object {
        const val VIEW_TYPE = 1
    }
    override fun getViewType() = VIEW_TYPE
    override fun getId() = day.toString()
}

data class RappelRow(val id: UUID, val sujet: String, val date: LocalDateTime): CalendarRow() {
    companion object {
        const val VIEW_TYPE = 2
    }
    override fun getViewType() = VIEW_TYPE
    override fun getId() = id.toString()
}

data class VisiteRow(val id: UUID, val address: String, val contact: String, val date: LocalDateTime): CalendarRow() {
    companion object {
        const val VIEW_TYPE = 3
    }
    override fun getViewType() = VIEW_TYPE
    override fun getId() = id.toString()
}

data class EstimationRow(val id: UUID, val address: String, val contact: String, val date: LocalDateTime): CalendarRow() {
    companion object {
        const val VIEW_TYPE = 4
    }
    override fun getViewType() = VIEW_TYPE
    override fun getId() = id.toString()
}

object SeparatorRow: CalendarRow() {
    const val VIEW_TYPE = 5
    override fun getViewType() = VIEW_TYPE
    override fun getId() = "separator"
}