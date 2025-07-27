package de.stubbe.interlude.util

object ExternalLinkHandler {
    private var cached: String? = null

    var listener: ((uri: String) -> Unit)? = null
        set(value) {
            field = value
            if (value != null) {
                cached?.let { value.invoke(it) }
                cached = null
            }
        }

    fun onNewLink(uri: String) {
        cached = uri
        listener?.let {
            it.invoke(uri)
            cached = null
        }
    }
}