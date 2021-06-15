package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import fr.julocorp.jenisassistant.ui.calendar.list.*
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

class ListEvents @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val rendezVousEstimationRepository: RendezVousEstimationRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun handle(): ActionState<List<CalendarRow>> = withContext(coroutineContextProvider.iO) {
        val calendarRowsGroupedByDate = TreeMap<LocalDate, MutableList<CalendarRow>>()
        calendarRowsGroupedByDate.putIfAbsent(
            LocalDate.now(),
            mutableListOf()
        )

        val deferredRappels = async { rappelRepository.findRappels() }
        val deferredRendezvousEstimations = async { rendezVousEstimationRepository.findRendezVousEstimations() }

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
                    calendarRowsGroupedByDate[rendezVousEstimation.rendezVousDate.toLocalDate()]?.add(row)
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