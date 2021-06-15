package fr.julocorp.jenisassistant.domain.common

sealed class ActionState<T>

class Loading<T>: ActionState<T>()
data class Success<T>(val result: T): ActionState<T>()
data class Failure<T>(val error: Throwable): ActionState<T>()