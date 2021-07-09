package fr.julocorp.jenisassistant.calendar.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.calendar.useCase.ScheduleRendezVousEstimation
import fr.julocorp.jenisassistant.domain.calendar.useCase.SearchFullAddressPropositionsWithPartialAddress
import fr.julocorp.jenisassistant.ui.calendar.schedule.CalendarEstimationViewModel
import fr.julocorp.jenisassistant.ui.calendar.schedule.RendezVousEstimationInput
import fr.julocorp.jenisassistant.utils.observeOnce
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime

@RunWith(MockitoJUnitRunner::class)
class CalendarEstimationViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `update rendez vous estimation input return a live date with same values`() {
        val input = RendezVousEstimationInput(
            rendezVousDate = LocalDateTime.now(),
            fullName = "Clark Kent",
            email = "c.kent@daily-planet.com"
        )

        val viewModel = CalendarEstimationViewModel(
            mock(SearchFullAddressPropositionsWithPartialAddress::class.java),
            mock(ScheduleRendezVousEstimation::class.java),
            TestCoroutineContextProvider()
        )

        viewModel.updateRendezvousInput(input)

        viewModel.rendezVousEstimationInput.observeOnce { inputUpdated ->
            assertThat(inputUpdated.rendezVousDate).isEqualTo(input.rendezVousDate)
            assertThat(inputUpdated.fullName).isEqualTo(input.fullName)
            assertThat(inputUpdated.email).isEqualTo(input.email)
        }
    }
}