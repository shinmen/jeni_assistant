package fr.julocorp.jenisassistant.calendar.useCase

import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.Failure
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.useCase.EndRappel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.*
import java.time.LocalDateTime
import java.util.*


class EndRappelUseCaseTest {
    @Test
    fun `end rappel should be successfull`() = runBlockingTest {
        val repo = mock(RappelRepository::class.java)
        val useCase = EndRappel(repo, TestCoroutineContextProvider())
        val rappel = Rappel(UUID.randomUUID(), LocalDateTime.now(), "sujet")

        assertThat(useCase.handle(rappel.id)).isInstanceOf(Success::class.java)
    }

    @Test
    fun `end rappel with error should fail`() = runBlockingTest {
        val repo = mock(RappelRepository::class.java)
        val id = UUID.randomUUID()
        doThrow(RuntimeException()).`when`(repo).endRappel(id)

        val useCase = EndRappel(repo, TestCoroutineContextProvider())

        assertThat(useCase.handle(id)).isInstanceOf(Failure::class.java)
    }
}