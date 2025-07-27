package de.stubbe.interlude.util

import android.content.Context
import android.os.Build
import java.util.Locale

actual fun applyLanguage(iso: String, context: Any?) {
    val androidContext = context as Context

    val locale = if (Build.VERSION.SDK_INT >= 36) {
        Locale.of(iso)
    } else {
        Locale(iso)
    }
    Locale.setDefault(locale)
    context.resources.configuration.setLocale(locale)
}