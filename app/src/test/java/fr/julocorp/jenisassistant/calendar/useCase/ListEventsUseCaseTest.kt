package fr.julocorp.jenisassistant.calendar.useCase

import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRappel
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRendezVousEstimation
import fr.julocorp.jenisassistant.domain.calendar.useCase.ListEvents
import fr.julocorp.jenisassistant.domain.common.*
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.domain.prospection.Contact
import fr.julocorp.jenisassistant.domain.prospection.ContactOrigin
import fr.julocorp.jenisassistant.ui.calendar.list.*
import fr.julocorp.jenisassistant.utils.observeOnce
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.util.*

class ListEventsUseCaseTest {
    private val address by lazy {
        FullAddress(
            "1 rue du parc",
            null,
            null,
            "33000",
            "Bordeaux",
            Geolocation(1.0, 1.0)
        )
    }

    private val proprietaire by lazy {
        Contact(ContactOrigin.INOPINE, "Clark Kent", null, null, null)
    }

    @Test
    fun `fetch calendar rows should return list with days and separators`() = runBlockingTest {
        val rappelRepo = mock(RappelRepository::class.java)
        val rdvEstimationRepo = mock(RendezVousEstimationRepository::class.java)
        val now =  LocalDateTime.now()
        `when`(rappelRepo.findRappels()).thenReturn(listOf(
            Rappel(UUID.randomUUID(), now.plusDays(1), "sujet1"),
            Rappel(UUID.randomUUID(), now.minusHours(1), "sujet2"),
            Rappel(UUID.randomUUID(), now.plusHours(1), "sujet3"),
        ))

        `when`(rdvEstimationRepo.findRendezVousEstimations()).thenReturn(listOf(
            RendezVousEstimation(UUID.randomUUID(), now.plusDays(1), address, proprietaire),
            RendezVousEstimation(UUID.randomUUID(), now, address, proprietaire),
        ))

        val useCase = ListEvents(rappelRepo, rdvEstimationRepo)
        val result = useCase.handle()

        assertThat(result).isInstanceOf(Success::class.java)
        val rows = (result as Success).result
        assertThat(rows).hasSize(9)
        assertThat(rows.filterIsInstance<SeparatorRow>()).hasSize(2)
        assertThat(rows.filterIsInstance<DayRow>()).hasSize(2)
        assertThat(rows.filterIsInstance<RappelRow>()).hasSize(3)
        assertThat(rows.filterIsInstance<EstimationRow>()).hasSize(2)
    }

    @Test()
    fun `when one repository fails, use case should return Failure`() = runBlockingTest {
        val rappelRepository = mock(RappelRepository::class.java).also {
            `when`(it.findRappels()).thenThrow(RuntimeException())
        }
        val rendezVousEstimationRepository = mock(RendezVousEstimationRepository::class.java).also {
            `when`(it.findRendezVousEstimations()).thenReturn(listOf())
        }

        val useCase = ListEvents(
            rappelRepository,
            rendezVousEstimationRepository,
        )

        assertThat(useCase.handle()).isInstanceOf(Failure::class.java)
    }
}