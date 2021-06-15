package fr.julocorp.jenisassistant

import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider() {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    override val main: CoroutineContext = testCoroutineDispatcher
    override val iO: CoroutineContext = testCoroutineDispatcher
    override val default: CoroutineContext = testCoroutineDispatcher
}