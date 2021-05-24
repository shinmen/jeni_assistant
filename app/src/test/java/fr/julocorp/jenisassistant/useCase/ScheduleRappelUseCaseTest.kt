package fr.julocorp.jenisassistant.useCase

import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.common.useCase.ScheduleRappel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ScheduleRappelUseCaseTest {
    @Test
    fun `schedule rappel should be successfull`() {
        val repo = mock(RappelRepository::class.java)
        val useCase = ScheduleRappel(repo)
        val rappel = Rappel(UUID.randomUUID(), Calendar.getInstance(), "sujet")

        runBlocking {
            assertTrue(useCase.handle(rappel) is Success)
        }
    }
}