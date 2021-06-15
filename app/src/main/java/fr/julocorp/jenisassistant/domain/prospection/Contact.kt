package fr.julocorp.jenisassistant.domain.prospection

data class Contact(
    val type: ContactOrigin,
    val fullname: String,
    val phoneNumber: PhoneNumber?,
    val email: Email?,
    val commentaire: String?,
)
