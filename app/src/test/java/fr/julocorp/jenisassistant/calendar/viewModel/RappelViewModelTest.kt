package fr.julocorp.jenisassistant.calendar.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.calendar.exception.RappelSujetEmpty
import fr.julocorp.jenisassistant.domain.calendar.useCase.ScheduleRappel
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.ui.calendar.schedule.RappelViewModel
import fr.julocorp.jenisassistant.utils.observeOnce
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class RappelViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `when repository succeed, live data return success`() = runBlockingTest {
        val rappel = Rappel(
            UUID.randomUUID(),
            LocalDateTime.now(),
            "not empty",
            null
        )

        val useCaseScheduleRappel = Mockito.mock(ScheduleRappel::class.java).also{
            `when`(it.handle(rappel)).thenReturn(Success(true))
        }
        val viewModel = RappelViewModel(
            useCaseScheduleRappel,
            TestCoroutineContextProvider()
        )
        viewModel.scheduleRappelResult.observeOnce {
            Truth.assertThat(it).isInstanceOf(Loading::class.java)
        }
        viewModel.schedule(rappel)

        viewModel.scheduleRappelResult.observeOnce {
            Truth.assertThat(it).isInstanceOf(Success::class.java)
        }
    }

    @Test
    fun `when repository trigger error, live data return failure`() = runBlockingTest {
        val rappel = Rappel(
            UUID.randomUUID(),
            LocalDateTime.now(),
            "",
            null
        )

        val useCaseScheduleRappel = Mockito.mock(ScheduleRappel::class.java).also{
            `when`(it.handle(rappel)).thenReturn(Failure(RappelSujetEmpty()))
        }
        val viewModel = RappelViewModel(
            useCaseScheduleRappel,
            TestCoroutineContextProvider()
        )
        viewModel.scheduleRappelResult.observeOnce {
            Truth.assertThat(it).isInstanceOf(Loading::class.java)
        }
        viewModel.schedule(rappel)

        viewModel.scheduleRappelResult.observeOnce {
            Truth.assertThat(it).isInstanceOf(Failure::class.java)
        }
    }
}