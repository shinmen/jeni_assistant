package fr.julocorp.jenisassistant.domain.prospection

data class Contact(
    val type: ContactOrigin,
    val firstname: String,
    val lastname: String,
    val adresse: AdresseProspect,
    val phoneNumber: PhoneNumber?,
    val email: Email?,
    val detail: String?,
)

typealias PhoneNumber = String
typealias Email = String
