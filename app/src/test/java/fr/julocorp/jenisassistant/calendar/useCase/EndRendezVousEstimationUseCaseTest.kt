package fr.julocorp.jenisassistant.calendar.useCase

import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRappel
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRendezVousEstimation
import fr.julocorp.jenisassistant.domain.common.*
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.domain.prospection.Contact
import fr.julocorp.jenisassistant.domain.prospection.ContactOrigin
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*


class EndRendezVousEstimationUseCaseTest {
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
    fun `end rendez vous estimation should be successfull`() = runBlockingTest {
        val repo = mock(RendezVousEstimationRepository::class.java)
        val useCase = EndRendezVousEstimation(repo, TestCoroutineContextProvider())
        val rendezvousEstimation = RendezVousEstimation(
            UUID.randomUUID(),
            LocalDateTime.now(),
            address,
            proprietaire,
            UUID.randomUUID()
        )

        assertThat(useCase.handle(rendezvousEstimation.id)).isInstanceOf(Success::class.java)
    }

    @Test
    fun `end rendez vous estimation with error should return failure`() = runBlockingTest {
        val repo = mock(RendezVousEstimationRepository::class.java)
        val id = UUID.randomUUID()
        doThrow(RuntimeException()).`when`(repo).endRendezVousEstimation(id)

        val useCase = EndRendezVousEstimation(repo, TestCoroutineContextProvider())

        assertThat(useCase.handle(id)).isInstanceOf(Failure::class.java)
    }
}