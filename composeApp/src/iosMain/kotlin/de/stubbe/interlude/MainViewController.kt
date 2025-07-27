package de.stubbe.interlude

import androidx.compose.ui.window.ComposeUIViewController
import de.stubbe.interlude.platform.di.initKoin
import de.stubbe.interlude.platform.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}