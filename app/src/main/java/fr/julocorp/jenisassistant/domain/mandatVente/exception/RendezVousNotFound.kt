package fr.julocorp.jenisassistant.domain.mandatVente.exception

import java.lang.RuntimeException

class RendezVousNotFound(override val message: String? = "Ce rendez-vous n'existe pas"): RuntimeException() {
}