package fr.julocorp.jenisassistant.infrastructure

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

open class CoroutineContextProvider @Inject constructor() {
    open val main: CoroutineContext by lazy { Dispatchers.Main }
    open val iO: CoroutineContext by lazy { Dispatchers.IO }
    open val default: CoroutineContext by lazy { Dispatchers.Default }
}