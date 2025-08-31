package de.stubbe.interlude.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.stubbe.interlude.domain.model.Language
import de.stubbe.interlude.domain.model.ThemeMode
import de.stubbe.interlude.platform.ui.Colors
import de.stubbe.interlude.platform.ui.Constants
import de.stubbe.interlude.presentation.components.DropDownMenu
import de.stubbe.interlude.presentation.viewmodel.SettingsViewModel
import de.stubbe.interlude.util.applyLanguage
import de.stubbe.interlude.util.getPlatformContext
import dev.burnoo.compose.remembersetting.rememberStringSetting
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.dark
import interlude.composeapp.generated.resources.delete_history
import interlude.composeapp.generated.resources.english
import interlude.composeapp.generated.resources.german
import interlude.composeapp.generated.resources.history_deleted
import interlude.composeapp.generated.resources.language
import interlude.composeapp.generated.resources.light
import interlude.composeapp.generated.resources.system
import interlude.composeapp.generated.resources.theme
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = koinViewModel()

    var themeMode by rememberStringSetting(
        key = Constants.THEME_MODE_KEY,
        defaultValue = ThemeMode.SYSTEM.name
    )
    val selectedTheme by derivedStateOf {
        ThemeMode.entries.first { it.name == themeMode }
    }

    var languageIso by rememberStringSetting(
        key = Constants.LANGUAGE_ISO_KEY,
        defaultValue = Language.GERMAN.iso
    )
    val selectedLanguage by derivedStateOf {
        Language.entries.first { it.iso == languageIso }
    }

    val context = getPlatformContext()

    val toastBGColor = Colors.Accent
    val toastTextColor = Colors.OnAccent

    val themeText = stringResource(Res.string.theme)
    val systemText = stringResource(Res.string.system)
    val lightText = stringResource(Res.string.light)
    val darkText = stringResource(Res.string.dark)
    val languageText = stringResource(Res.string.language)
    val germanText = stringResource(Res.string.german)
    val englishText = stringResource(Res.string.english)
    val historyDeletedText = stringResource(Res.string.history_deleted)
    val deleteHistoryText = stringResource(Res.string.delete_history)

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
                text = themeText,
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
                            themeMode = mode.name
                            Colors.setThemeMode(mode)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Colors.Accent else Colors.Background
                        ),
                        shape = Constants.Shape.Rounded.Small,
                        border = BorderStroke(Constants.BorderWidthSmall, Colors.Border),
                        contentPadding = PaddingValues(Constants.PaddingSmall)
                    ) {
                        Text(
                            text = when (mode) {
                                ThemeMode.SYSTEM -> systemText
                                ThemeMode.LIGHT -> lightText
                                ThemeMode.DARK -> darkText
                            },
                            color = if (isSelected) Colors.OnAccent else Colors.Text,
                            fontSize = Constants.FontSizeSmall
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = languageText,
                color = Colors.UnselectedText,
                fontSize = Constants.FontSizeSmall
            )
            DropDownMenu(
                items = Language.entries,
                getTitle = {
                    when (it) {
                        Language.GERMAN -> germanText
                        Language.ENGLISH -> englishText
                    }
                },
                selectedItem = selectedLanguage,
                onItemSelected = { newLanguage ->
                    languageIso = newLanguage.iso
                    applyLanguage(newLanguage.iso, context)
                }
            )
        }

        Button(
            onClick = {
                viewModel.clearHistory()
                showToast(
                    message = historyDeletedText,
                    backgroundColor = toastBGColor,
                    textColor = toastTextColor,
                    duration = ToastDuration.Short
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Constants.ButtonHeightMedium),
            colors = ButtonDefaults.buttonColors(containerColor = Colors.Accent),
            shape = Constants.Shape.Rounded.Small
        ) {
            Text(
                deleteHistoryText,
                color = Colors.OnAccent,
                fontSize = Constants.FontSizeMedium
            )
        }
    }
}