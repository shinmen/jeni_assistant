package fr.julocorp.jenisassistant.domain

sealed class Result

data class Success(val result: Any): Result()

data class Failure(val error: Throwable): Result()