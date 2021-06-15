package fr.julocorp.jenisassistant.infrastructure.calendar.repository

import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.domain.prospection.Contact
import fr.julocorp.jenisassistant.domain.prospection.ContactOrigin
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import fr.julocorp.jenisassistant.infrastructure.calendar.database.CalendarEstimationDao
import fr.julocorp.jenisassistant.infrastructure.calendar.database.CalendarEstimationEntity
import fr.julocorp.jenisassistant.infrastructure.calendar.database.CalendarEstimationWithProprietaireDao
import fr.julocorp.jenisassistant.infrastructure.calendar.database.ProprietaireEntity
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class LocalDBRendezVousEstimationRepository @Inject constructor(
    private val calendarEstimationDao: CalendarEstimationDao,
    private val calendarEstimationWithProprietaireDao: CalendarEstimationWithProprietaireDao,
    private val coroutineContextProvider: CoroutineContextProvider
) : RendezVousEstimationRepository {
    override suspend fun scheduleRendezVousEstimation(rendezVousEstimation: RendezVousEstimation) =
        withContext(coroutineContextProvider.iO) {
            val proprietaire = ProprietaireEntity(
                UUID.randomUUID(),
                rendezVousEstimation.prospect.fullname,
                rendezVousEstimation.prospect.phoneNumber,
                rendezVousEstimation.prospect.email,
                rendezVousEstimation.prospect.commentaire
            )
            val calendarEstimation = CalendarEstimationEntity(
                rendezVousEstimation.id,
                rendezVousEstimation.rendezVousDate.atZone(ZoneId.of("Europe/Paris"))
                    .toEpochSecond(),
                proprietaire.id,
                rendezVousEstimation.addresseBien,
            )

            calendarEstimationWithProprietaireDao.insertCalendarEstimationWithProprietaire(
                calendarEstimation,
                proprietaire
            )
        }

    override suspend fun endRendezVousEstimation(rendezVousEstimationId: UUID) {
        calendarEstimationDao.cancelRendezVousEstimation(
            rendezVousEstimationId,
            LocalDateTime.now().atZone(ZoneId.of("Europe/Paris")).toEpochSecond()
        )
    }

    override suspend fun findRendezVousEstimations(): List<RendezVousEstimation> =
        calendarEstimationWithProprietaireDao.getOnGoingRendezvousEstimation()
            .map { calendarEstimationEntity ->
                val rendezVousDate = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(calendarEstimationEntity.calendarEstimation.rendezVousDate),
                    ZoneId.of("Europe/Paris")
                )
                RendezVousEstimation(
                    calendarEstimationEntity.calendarEstimation.id,
                    rendezVousDate,
                    calendarEstimationEntity.calendarEstimation.addresse,
                    Contact(
                        ContactOrigin.INOPINE,
                        calendarEstimationEntity.proprietaire.fullname,
                        calendarEstimationEntity.proprietaire.phoneNumber,
                        calendarEstimationEntity.proprietaire.email,
                        calendarEstimationEntity.proprietaire.commentaire,
                    )
                )
            }
}