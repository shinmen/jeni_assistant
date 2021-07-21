package fr.julocorp.jenisassistant.infrastructure

interface TypeIdentifier {
    fun getViewType(): ViewType
    fun getId(): String
}