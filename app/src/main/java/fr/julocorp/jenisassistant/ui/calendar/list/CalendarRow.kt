package fr.julocorp.jenisassistant.ui.calendar.list

import fr.julocorp.jenisassistant.infrastructure.TypeIdentifier
import java.util.*

sealed class CalendarRow: TypeIdentifier

data class DayRow(val day: Calendar): CalendarRow() {
    companion object {
        const val VIEW_TYPE = 1
    }
    override fun getViewType() = VIEW_TYPE
}

data class TaskRow(val id: UUID, val reminder: String, val date: Calendar): CalendarRow() {
    companion object {
        const val VIEW_TYPE = 2
    }
    override fun getViewType() = VIEW_TYPE
}

data class VisiteRow(val address: String, val contact: String, val date: Calendar): CalendarRow() {
    companion object {
        const val VIEW_TYPE = 3
    }
    override fun getViewType() = VIEW_TYPE
}

data class EstimationRow(val address: String, val contact: String, val date: Calendar): CalendarRow() {
    companion object {
        const val VIEW_TYPE = 4
    }
    override fun getViewType() = VIEW_TYPE
}

object SeparatorRow: CalendarRow() {
    const val VIEW_TYPE = 5
    override fun getViewType() = VIEW_TYPE
}