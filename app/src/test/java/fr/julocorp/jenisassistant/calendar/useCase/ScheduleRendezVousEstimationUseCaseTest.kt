package fr.julocorp.jenisassistant.calendar.useCase

import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.calendar.useCase.ScheduleRendezVousEstimation
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.domain.prospection.Contact
import fr.julocorp.jenisassistant.domain.prospection.ContactOrigin
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ScheduleRendezVousEstimationUseCaseTest {
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
    fun `schedule rendez vous estimation with valid inputs should be successfull`() =
        runBlockingTest {
            val repo = mock(RendezVousEstimationRepository::class.java)
            val useCase = ScheduleRendezVousEstimation(repo)
            val rendezvousEstimation = RendezVousEstimation(
                UUID.randomUUID(),
                LocalDateTime.now(),
                address,
                proprietaire
            )

            assertThat(useCase.handle(rendezvousEstimation)).isInstanceOf(Success::class.java)
        }

    @Test
    fun `schedule rendez vous estimation with error should return failure`() = runBlockingTest {
        val repo = mock(RendezVousEstimationRepository::class.java)
        val rendezvousEstimation = RendezVousEstimation(
            UUID.randomUUID(),
            LocalDateTime.now(),
            address,
            proprietaire
        )
        `when`(repo.scheduleRendezVousEstimation(rendezvousEstimation)).thenThrow(RuntimeException())
        val useCase = ScheduleRendezVousEstimation(repo)


        val result = useCase.handle(rendezvousEstimation)

        assertThat(result).isInstanceOf(Failure::class.java)
    }
}