package fr.julocorp.jenisassistant.calendar.useCase

import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.useCase.ListEvents
import fr.julocorp.jenisassistant.ui.calendar.list.CalendarRow
import fr.julocorp.jenisassistant.ui.calendar.list.DayRow
import fr.julocorp.jenisassistant.ui.calendar.list.RappelRow
import fr.julocorp.jenisassistant.ui.calendar.list.SeparatorRow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import java.util.*

class ListEventsUseCaseTest {
    @Test
    fun `fetch calendar rows should return list with days and separators`() = runBlockingTest {
        val repo = Mockito.mock(RappelRepository::class.java)
        val now =  LocalDateTime.now()
        `when`(repo.findRappels()).thenReturn(listOf(
            Rappel(UUID.randomUUID(), now.plusDays(1), "sujet1"),
            Rappel(UUID.randomUUID(), now.minusHours(1), "sujet2"),
            Rappel(UUID.randomUUID(), now.plusHours(1), "sujet3"),
        ))
        val useCase = ListEvents(repo, TestCoroutineContextProvider())

        val result = useCase.handle()

        assertThat(result).isInstanceOf(Success::class.java)
        val rows = (result as Success).result as List<CalendarRow>
        assertThat(rows).hasSize(7)
        assertThat(rows.filterIsInstance<SeparatorRow>()).hasSize(2)
        assertThat(rows.filterIsInstance<DayRow>()).hasSize(2)
        assertThat(rows.filterIsInstance<RappelRow>()).hasSize(3)
    }
}