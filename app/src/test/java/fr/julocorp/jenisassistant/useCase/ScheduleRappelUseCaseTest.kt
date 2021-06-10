package fr.julocorp.jenisassistant.useCase

import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.Failure
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.common.useCase.RappelSujetEmpty
import fr.julocorp.jenisassistant.domain.common.useCase.ScheduleRappel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ScheduleRappelUseCaseTest {
    @Test
    fun `schedule rappel with valid inputs should be successfull`() = runBlockingTest {
        val repo = mock(RappelRepository::class.java)
        val useCase = ScheduleRappel(repo, TestCoroutineContextProvider())
        val rappel = Rappel(UUID.randomUUID(), LocalDateTime.now(), "sujet")

        assertThat(useCase.handle(rappel)).isInstanceOf(Success::class.java)
    }

    @Test
    fun `schedule rappel without sujet should fail`() = runBlockingTest {
        val repo = mock(RappelRepository::class.java)
        val useCase = ScheduleRappel(repo, TestCoroutineContextProvider())
        val rappel = Rappel(UUID.randomUUID(), LocalDateTime.now(), "")

        val result = useCase.handle(rappel)

        assertThat(result).isInstanceOf(Failure::class.java)
        assertThat((result as Failure).error).isInstanceOf(RappelSujetEmpty::class.java)
    }
}