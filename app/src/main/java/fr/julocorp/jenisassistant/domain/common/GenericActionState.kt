package fr.julocorp.jenisassistant.domain

sealed class GenericActionState

object Loading: GenericActionState()
data class Success(val result: Any): GenericActionState()
data class Failure(val error: Throwable): GenericActionState()
object Finished: GenericActionState()