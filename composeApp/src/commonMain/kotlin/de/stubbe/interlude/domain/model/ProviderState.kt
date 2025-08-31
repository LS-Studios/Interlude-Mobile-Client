package de.stubbe.interlude.domain.model

data class ProviderState(
    val isLoading: Boolean = false,
    val providers: List<Provider> = emptyList(),
    val errorMessage: String? = null
)
