package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.GenericActionState
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarRow
import fr.julocorp.jenisassistant.ui.calendar.list.DayRow
import fr.julocorp.jenisassistant.ui.calendar.list.RappelRow
import fr.julocorp.jenisassistant.ui.calendar.list.SeparatorRow
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class ListEvents @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun handle(): GenericActionState = withContext(coroutineContextProvider.iO) {
        val calendarRowsGroupedByDate = TreeMap<LocalDate, MutableList<CalendarRow>>()
        calendarRowsGroupedByDate.putIfAbsent(
            LocalDate.now(),
            mutableListOf()
        )

        val deferredRappels = async { rappelRepository.findRappels() }
        deferredRappels.await().also { rappels ->
            rappels
                .map { rappel ->
                    val row = RappelRow(rappel.id, rappel.sujet, rappel.rappelDate)
                    calendarRowsGroupedByDate.putIfAbsent(
                        rappel.rappelDate.toLocalDate(),
                        mutableListOf()
                    )
                    calendarRowsGroupedByDate[rappel.rappelDate.toLocalDate()]?.add(row)
                }
        }

        val calendarRows = calendarRowsGroupedByDate
                .map { rowsGroupByDate ->
                    val isToday = LocalDate.now().equals(rowsGroupByDate.key)
                    if (rowsGroupByDate.value.isNotEmpty() || isToday) {
                        rowsGroupByDate.value.add(0, DayRow(rowsGroupByDate.key, isToday))
                        rowsGroupByDate.value.add(SeparatorRow)
                    }

                    rowsGroupByDate
                }
                .flatMap { rowsGroupByDate -> rowsGroupByDate.value.toList() }

        Success(calendarRows)
    }
}