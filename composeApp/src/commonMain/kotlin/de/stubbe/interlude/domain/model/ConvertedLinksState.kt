package de.stubbe.interlude.domain.model

data class ConvertedLinksState(
    val isLoading: Boolean = false,
    val convertedLinks: List<ConvertedLink> = emptyList(),
    val errorMessage: String? = null
)
