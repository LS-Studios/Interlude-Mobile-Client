package de.stubbe.interlude.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import de.stubbe.interlude.model.Language
import de.stubbe.interlude.model.ThemeMode
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants
import de.stubbe.interlude.view.components.DropDownMenu
import de.stubbe.interlude.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel()

    val selectedTheme by viewModel.selectedTheme.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val historyEnabled by viewModel.historyEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.PaddingLarge),
        verticalArrangement = Arrangement.spacedBy(Constants.SpacerXLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Theme",
                color = Colors.UnselectedText,
                fontSize = Constants.FontSizeSmall
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(Constants.SpacerSmall)
            ) {
                ThemeMode.entries.forEach { mode ->
                    val isSelected = mode == selectedTheme
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.setTheme(mode)
                            Colors.setThemeMode(mode)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Colors.Accent else Colors.Background
                        ),
                        shape = Constants.Shape.Rounded.Small,
                        border = BorderStroke(Constants.BorderWidthSmall, Colors.Border),
                        contentPadding = PaddingValues(Constants.PaddingSmall)
                    ) {
                        Text(mode.name, color = Colors.Text, fontSize = Constants.FontSizeSmall)
                    }
                }
            }
        }

        DropDownMenu(
            items = Language.entries,
            getTitle = { when (it) {
                Language.GERMAN -> "German"
                Language.ENGLISH -> "English"
            } },
            selectedItem = selectedLanguage,
            onItemSelected = { viewModel.setLanguage(it) }
        )

        Button(
            onClick = {
                viewModel.clearHistory()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Constants.ButtonHeightMedium),
            colors = ButtonDefaults.buttonColors(containerColor = Colors.Accent),
            shape = Constants.Shape.Rounded.Small
        ) {
            Text(
                "Delete history",
                color = Colors.Text,
                fontSize = Constants.FontSizeMedium
            )
        }
    }
}

private @Composable
fun DropdownMenuSetting(
    current: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            modifier = modifier,
            onClick = { expanded = true },
            shape = Constants.Shape.Rounded.Small,
            border = BorderStroke(Constants.BorderWidthSmall, Colors.Border),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Colors.Main)
        ) {
            Text(current, color = Colors.Text, fontSize = Constants.FontSizeSmall)
        }

        DropdownMenu(
            modifier = modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    onClick = {
                        onSelect(it)
                        expanded = false
                    },
                    text = { Text(it, color = Colors.Text) }
                )
            }
        }
    }
}