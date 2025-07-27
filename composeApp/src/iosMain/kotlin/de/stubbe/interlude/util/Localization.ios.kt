package de.stubbe.interlude.util

import platform.Foundation.NSUserDefaults

actual fun applyLanguage(iso: String, context: Any?) {
    NSUserDefaults.standardUserDefaults.setObject(
        arrayListOf(iso), "AppleLanguages"
    )
}