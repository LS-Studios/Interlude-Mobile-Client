package de.stubbe.interlude.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow

class AppShareRepository {

    private val _injectedLink: MutableStateFlow<String?> = MutableStateFlow(null)
    val injectedLink: MutableStateFlow<String?> = _injectedLink

    fun setInjectedLink(link: String?) {
        if (link == null) return

        val regex = Regex("""https?://[^\s]+""")
        val match = regex.find(link)

        val parsedLink = match?.value
        if (parsedLink != null) {
            _injectedLink.value = parsedLink
        }
    }

}