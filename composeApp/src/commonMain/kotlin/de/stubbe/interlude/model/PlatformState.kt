package de.stubbe.interlude.model

data class PlatformState(
    val isLoading: Boolean = false,
    val providers: List<Provider> = emptyList(),
    val errorMessage: String? = null
)
