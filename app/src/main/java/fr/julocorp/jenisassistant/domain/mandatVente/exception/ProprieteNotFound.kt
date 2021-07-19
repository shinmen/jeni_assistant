package fr.julocorp.jenisassistant.domain.mandatVente.exception

import java.lang.RuntimeException

class ProprieteNotFound(override val message: String? = "Cette propriété n'existe pas"): RuntimeException() {
}