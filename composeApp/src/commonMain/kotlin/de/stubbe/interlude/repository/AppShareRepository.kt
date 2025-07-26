package de.stubbe.interlude.repository

import kotlinx.coroutines.flow.MutableStateFlow

class AppShareRepository {

    private val _injectedLink: MutableStateFlow<String?> = MutableStateFlow(null)
    val injectedLink: MutableStateFlow<String?> = _injectedLink

    fun setInjectedLink(link: String?) {
        _injectedLink.value = link
    }

}