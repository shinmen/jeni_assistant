package fr.julocorp.jenisassistant.calendar.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRappel
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRendezVousEstimation
import fr.julocorp.jenisassistant.domain.calendar.useCase.ListEvents
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RappelDao
import fr.julocorp.jenisassistant.infrastructure.calendar.repository.LocalDBRappelRepository
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarViewModel
import fr.julocorp.jenisassistant.utils.observeOnce
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CalendarViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `when repository return calendar rows, live data loads successfully`() = runBlockingTest {
        val useCaseListEvents = mock(ListEvents::class.java).also{
            `when`(it.handle()).thenReturn(Success(listOf()))
        }
        val viewModel = CalendarViewModel(
            useCaseListEvents,
            mock(EndRappel::class.java),
            mock(EndRendezVousEstimation::class.java),
            TestCoroutineContextProvider()
        )
        viewModel.calendarRows.observeOnce {
            assertThat(it).isInstanceOf(Loading::class.java)
        }
        viewModel.fetchCalendarRow()

        viewModel.calendarRows.observeOnce {
            assertThat(it).isInstanceOf(Success::class.java)
        }
    }

    @Test
    fun `when one repository fails to fetch calendar rows, live data loads and return failure`() = runBlockingTest {
        val useCaseListEvents = mock(ListEvents::class.java).also {
            `when`(it.handle()).thenReturn(Failure(RuntimeException()))
        }

        val viewModel = CalendarViewModel(
            useCaseListEvents,
            mock(EndRappel::class.java),
            mock(EndRendezVousEstimation::class.java),
            TestCoroutineContextProvider()
        )

        viewModel.calendarRows.observeOnce {
            assertThat(it).isInstanceOf(Loading::class.java)
        }

        viewModel.fetchCalendarRow()

        viewModel.calendarRows.observeOnce {
            assertThat(it).isInstanceOf(Failure::class.java)
        }
    }

    @Test
    fun `after one rappel was closed, calendar rows should refresh`() = runBlockingTest {
        val useCaseListEvents = mock(ListEvents::class.java).also {
            `when`(it.handle()).thenReturn(Success(listOf()))
        }

        val viewModel = CalendarViewModel(
            useCaseListEvents,
            mock(EndRappel::class.java),
            mock(EndRendezVousEstimation::class.java),
            TestCoroutineContextProvider()
        )

        viewModel.markRappelAsDone(UUID.randomUUID())

        viewModel.calendarRows.observeOnce {
            assertThat(it).isInstanceOf(Success::class.java)
        }
    }

    @Test
    fun `after one rdv estimation was closed, calendar rows should refresh`() = runBlockingTest {
        val useCaseListEvents = mock(ListEvents::class.java).also {
            `when`(it.handle()).thenReturn(Success(listOf()))
        }

        val viewModel = CalendarViewModel(
            useCaseListEvents,
            mock(EndRappel::class.java),
            mock(EndRendezVousEstimation::class.java),
            TestCoroutineContextProvider()
        )

        viewModel.markRendezvousEstimationAsDone(UUID.randomUUID())

        viewModel.calendarRows.observeOnce {
            assertThat(it).isInstanceOf(Success::class.java)
        }
    }

    @Test
    fun `after one rdv estimation triggers error, calendar rows should not block`() = runBlockingTest {
        val useCaseListEvents = mock(ListEvents::class.java).also {
            `when`(it.handle()).thenReturn(Success(listOf()))
        }
        val rappelId = UUID.randomUUID()
        val useCaseEndRappel = mock(EndRappel::class.java)

        val viewModel = CalendarViewModel(
            useCaseListEvents,
            useCaseEndRappel,
            mock(EndRendezVousEstimation::class.java),
            TestCoroutineContextProvider()
        )

        viewModel.markRendezvousEstimationAsDone(rappelId)

        viewModel.calendarRows.observeOnce {
            assertThat(it).isInstanceOf(Success::class.java)
        }
    }
}