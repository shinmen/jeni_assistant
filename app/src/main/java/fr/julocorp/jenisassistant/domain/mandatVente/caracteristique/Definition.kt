package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

data class Definition(
    val defaultLabel: String,
    val validationRules: List<String>,
    val availableValues: List<String>,
    val suffix: String? = null,
)
