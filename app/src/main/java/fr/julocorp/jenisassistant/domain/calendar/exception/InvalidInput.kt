package fr.julocorp.jenisassistant.domain.calendar.exception

open class InvalidInput(val validationErrors: Map<Int, Int> = mapOf()) : Throwable()