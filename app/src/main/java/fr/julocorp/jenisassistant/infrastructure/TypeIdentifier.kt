package fr.julocorp.jenisassistant.infrastructure

import java.util.*

interface TypeIdentifier {
    fun getViewType(): ViewType
    fun getId(): String
}