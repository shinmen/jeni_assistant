package fr.julocorp.jenisassistant.domain.common.useCase

import java.lang.RuntimeException

class RappelSujetEmpty(override val message: String? = "Il y a besoin d'un sujet au rappel ") : RuntimeException()