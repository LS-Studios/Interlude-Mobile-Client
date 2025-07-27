package de.stubbe.interlude

import androidx.compose.ui.window.ComposeUIViewController
import de.stubbe.interlude.di.initKoin
import de.stubbe.interlude.view.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}