package fr.julocorp.jenisassistant.useCase

import fr.julocorp.jenisassistant.domain.Failure
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.common.useCase.RappelSujetEmpty
import fr.julocorp.jenisassistant.domain.common.useCase.ScheduleRappel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ScheduleRappelUseCaseTest {
    @Test
    fun `schedule rappel with valid inputs should be successfull`() {
        val repo = mock(RappelRepository::class.java)
        val useCase = ScheduleRappel(repo)
        val rappel = Rappel(UUID.randomUUID(), LocalDateTime.now(), "sujet")

        runBlocking {
            assertTrue(useCase.handle(rappel) is Success)
        }
    }

    @Test
    fun `schedule rappel without sujet should fail`() {
        val repo = mock(RappelRepository::class.java)
        val useCase = ScheduleRappel(repo)
        val rappel = Rappel(UUID.randomUUID(), LocalDateTime.now(), "")

        runBlocking {
            val result = useCase.handle(rappel)
            assertTrue(result is Failure)
            assertTrue((result as Failure).error is RappelSujetEmpty)
        }
    }
}