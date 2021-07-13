package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.ui.calendar.list.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class ListEvents @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val rendezVousEstimationRepository: RendezVousEstimationRepository
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

    private fun mapToListWithDaysAndSeparator(
        calendarRowsGroupedByDate: TreeMap<LocalDate, MutableList<CalendarRow>>
    ): List<CalendarRow> = calendarRowsGroupedByDate
        .map { rowsGroupByDate ->
            val isToday = LocalDate.now().equals(rowsGroupByDate.key)
            if (rowsGroupByDate.value.isNotEmpty() || isToday) {
                rowsGroupByDate.value.add(0, DayRow(rowsGroupByDate.key, isToday))
                rowsGroupByDate.value.add(SeparatorRow)
            }

            rowsGroupByDate
        }
        .flatMap { rowsGroupByDate -> rowsGroupByDate.value.toList() }
}