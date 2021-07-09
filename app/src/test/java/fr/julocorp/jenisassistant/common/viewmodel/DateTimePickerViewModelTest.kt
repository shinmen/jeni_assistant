package fr.julocorp.jenisassistant.common.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.ui.common.datetimePicker.DateTimePickerViewModel
import fr.julocorp.jenisassistant.utils.observeOnce
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime

class DateTimePickerViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `when I change the date, live data should update with correct date and keep time`() {
        val viewModel = DateTimePickerViewModel()

        val now = LocalDateTime.now()
        val yesterday = LocalDateTime.now().minusDays(1)
        var datetimePicked: LocalDateTime? = null

        viewModel.dateTimePicked.observeOnce {
            datetimePicked = it
            assertThat(it.toLocalDate()).isEqualTo(now.toLocalDate())
        }

        viewModel.setDate(yesterday.toLocalDate())

        viewModel.dateTimePicked.observeOnce {
            assertThat(it.toLocalDate()).isEqualTo(yesterday.toLocalDate())
            assertThat(it.toLocalTime()).isEqualTo(datetimePicked?.toLocalTime())
        }
    }

    @Test
    fun `when I change the time, live data should update with correct time and keep date`() {
        val viewModel = DateTimePickerViewModel()

        val now = LocalDateTime.now()
        val yesterday = LocalDateTime.now().minusDays(1)
        var datetimePicked: LocalDateTime? = null

        viewModel.dateTimePicked.observeOnce {
            datetimePicked = it
            assertThat(
                "${it.toLocalTime().hour}:${it.toLocalTime().minute}"
            ).isEqualTo("${now.toLocalTime().hour}:${now.toLocalTime().minute}")
        }

        viewModel.setTime(yesterday.toLocalTime())

        viewModel.dateTimePicked.observeOnce {
            assertThat(it.toLocalTime()).isEqualTo(yesterday.toLocalTime())
            assertThat(it.toLocalDate()).isEqualTo(datetimePicked?.toLocalDate())
        }
    }
}