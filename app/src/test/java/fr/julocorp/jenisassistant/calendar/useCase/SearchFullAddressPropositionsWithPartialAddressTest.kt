package fr.julocorp.jenisassistant.calendar.useCase

import com.google.common.truth.Truth.assertThat
import fr.julocorp.jenisassistant.TestCoroutineContextProvider
import fr.julocorp.jenisassistant.domain.calendar.useCase.SearchFullAddressPropositionsWithPartialAddress
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class SearchFullAddressPropositionsWithPartialAddressTest {
    private val addressPropositions by lazy {
        listOf(
            FullAddress(
                "1 rue du parc",
                "1",
                "rue du parc",
                "33000",
                "Bordeaux",
                Geolocation(1.0, 1.0)
            )
        )
    }

    @Test
    fun `partial address match to full address should be successfull`() = runBlockingTest {
        val addressSearcher = mock(AdresseSearcher::class.java)
        val partialAddress = "1 rue du"
        `when`(addressSearcher.findByPartialAddress(partialAddress)).thenReturn(addressPropositions)
        val useCase = SearchFullAddressPropositionsWithPartialAddress(
            addressSearcher,
            TestCoroutineContextProvider()
        )

        val state = useCase.handle(partialAddress)

        assertThat(state).isInstanceOf(Success::class.java)
        assertThat((state as Success).result).isEqualTo(addressPropositions)
    }

    @Test
    fun `partial address triggers error should return failure`() = runBlockingTest {
        val addressSearcher = mock(AdresseSearcher::class.java)
        val partialAddress = "1 rue du"
        `when`(addressSearcher.findByPartialAddress(partialAddress)).thenThrow(RuntimeException())
        val useCase = SearchFullAddressPropositionsWithPartialAddress(
            addressSearcher,
            TestCoroutineContextProvider()
        )

        val state = useCase.handle(partialAddress)

        assertThat(state).isInstanceOf(Failure::class.java)
    }
}