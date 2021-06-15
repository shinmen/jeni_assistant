package fr.julocorp.jenisassistant.domain.prospection

enum class ContactOrigin(val origin: String) {
    APPEL_FSBO("FSBO"),
    APPEL_A_FROID("Appel à froid"),
    RESEAU("Réseau"),
    INOPINE("autre"),
    BOITAGE("boitage"),
}