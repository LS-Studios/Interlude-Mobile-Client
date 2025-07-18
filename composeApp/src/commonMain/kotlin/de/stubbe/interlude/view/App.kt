package de.stubbe.interlude.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.ThemeMode
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        LaunchedEffect(Unit) {
            Colors.setThemeMode(ThemeMode.DARK)
        }

        Navigation()
    }
}