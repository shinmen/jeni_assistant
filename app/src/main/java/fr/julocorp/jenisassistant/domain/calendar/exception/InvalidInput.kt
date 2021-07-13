package fr.julocorp.jenisassistant.domain.calendar.exception

import java.lang.RuntimeException

open class InvalidInput(val validationErrors: Map<Int, Int> = mapOf()) : RuntimeException()