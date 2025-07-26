package de.stubbe.interlude

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ComposeUIViewController
import de.stubbe.interlude.di.initKoin
import de.stubbe.interlude.repository.AppShareRepository
import de.stubbe.interlude.view.App
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import platform.Foundation.NSUserDefaults
import kotlin.getValue

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val repository: AppShareRepository = koinInject()

    LaunchedEffect(Unit) {
        val sharedLink = getSharedLink()
        repository.setInjectedLink(sharedLink)
    }

    App()
}

fun getSharedLink(): String? {
    val defaults = NSUserDefaults(suiteName = "group.de.stubbe.interlude")
    val link = defaults.stringForKey("shared_link")
    defaults.removeObjectForKey("shared_link")
    return link
}