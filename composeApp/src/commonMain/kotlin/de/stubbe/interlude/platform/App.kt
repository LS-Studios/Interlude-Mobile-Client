package de.stubbe.interlude.platform

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.domain.model.ThemeMode
import de.stubbe.interlude.ui.theme.Constants
import de.stubbe.interlude.presentation.Navigation
import dev.burnoo.compose.remembersetting.rememberStringSetting
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var themeMode by rememberStringSetting(
            key = Constants.THEME_MODE_KEY,
            defaultValue = ThemeMode.SYSTEM.name
        )
        val selectedTheme by derivedStateOf {
            ThemeMode.entries.first { it.name == themeMode }
        }

        LaunchedEffect(Unit) {
            Colors.setThemeMode(selectedTheme)
        }

        Navigation()
    }
}