package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import fr.julocorp.jenisassistant.ui.calendar.list.*
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import kotlin.Comparator

class ListEvents @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val rendezVousEstimationRepository: RendezVousEstimationRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun handle() =
        try {
            val calendarRowsGroupedByDate = TreeMap<LocalDate, MutableList<CalendarRow>>()
            calendarRowsGroupedByDate.putIfAbsent(
                LocalDate.now(),
                mutableListOf()
            )
            supervisorScope {
                val deferredRappels = async { rappelRepository.findRappels() }
                val deferredRendezvousEstimations = async {
                    rendezVousEstimationRepository.findRendezVousEstimations()
                }

                mapRappelsIntocalendarRows(deferredRappels, calendarRowsGroupedByDate)
                mapRendezVousEstimationIntoCalendarRows(
                    deferredRendezvousEstimations,
                    calendarRowsGroupedByDate
                )
            }

            Success(mapToListWithDaysAndSeparator(calendarRowsGroupedByDate))
        } catch (e: Throwable) {
            Failure(e)
        }


    private suspend fun mapRappelsIntocalendarRows(
        deferredRappels: Deferred<List<Rappel>>,
        calendarRowsGroupedByDate: TreeMap<LocalDate, MutableList<CalendarRow>>
    ) {
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
    }

    private suspend fun mapRendezVousEstimationIntoCalendarRows(
        deferredRendezvousEstimations: Deferred<List<RendezVousEstimation>>,
        calendarRowsGroupedByDate: TreeMap<LocalDate, MutableList<CalendarRow>>
    ) {
        deferredRendezvousEstimations.await().also { rendezVousEstimations ->
            rendezVousEstimations
                .map { rendezVousEstimation ->
                    val row = EstimationRow(
                        rendezVousEstimation.id,
                        rendezVousEstimation.addresseBien.toString(),
                        rendezVousEstimation.prospect.fullname,
                        rendezVousEstimation.prospect.phoneNumber,
                        rendezVousEstimation.prospect.email,
                        rendezVousEstimation.prospect.commentaire,
                        rendezVousEstimation.rendezVousDate
                    )
                    calendarRowsGroupedByDate.putIfAbsent(
                        rendezVousEstimation.rendezVousDate.toLocalDate(),
                        mutableListOf()
                    )
                    calendarRowsGroupedByDate[rendezVousEstimation.rendezVousDate.toLocalDate()]?.add(
                        row
                    )
                }
        }
    }

    private suspend fun mapToListWithDaysAndSeparator(
        calendarRowsGroupedByDate: TreeMap<LocalDate, MutableList<CalendarRow>>
    ): List<CalendarRow> = withContext(coroutineContextProvider.default) {
        calendarRowsGroupedByDate
            .map { rowsGroupByDate ->
                val sortedRowsGroupByDate = rowsGroupByDate.value
                    .filterIsInstance(DateTimeOfRow::class.java)
                    .sortedBy { it.getDateTime() }
                    .filterIsInstance(CalendarRow::class.java)
                    .toMutableList()

                val isToday = LocalDate.now().equals(rowsGroupByDate.key)
                if (sortedRowsGroupByDate.isNotEmpty() || isToday) {
                    sortedRowsGroupByDate.add(0, DayRow(rowsGroupByDate.key, isToday))
                    sortedRowsGroupByDate.add(SeparatorRow)
                }

                sortedRowsGroupByDate
            }
            .flatMap { sortedRowsGroupByDate -> sortedRowsGroupByDate.toList() }
            .dropLast(1)
    }
}