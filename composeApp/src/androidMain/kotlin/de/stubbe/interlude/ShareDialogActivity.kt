package de.stubbe.interlude

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import de.stubbe.interlude.domain.model.ThemeMode
import de.stubbe.interlude.presentation.dialogs.ConvertedLinksDialog
import de.stubbe.interlude.presentation.viewmodel.ShareDialogViewModel
import de.stubbe.interlude.platform.App
import de.stubbe.interlude.platform.ui.Colors
import de.stubbe.interlude.platform.ui.Constants
import dev.burnoo.compose.remembersetting.rememberStringSetting
import org.koin.android.ext.android.inject
import org.koin.compose.KoinContext

class ShareDialogActivity : ComponentActivity() {
    private val viewModel: ShareDialogViewModel by inject()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get shared text
        val sharedText = when {
            intent?.action == android.content.Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    intent.getStringExtra(android.content.Intent.EXTRA_TEXT)
                } else null
            }
            else -> null
        }
        
        setContent {
            MaterialTheme {
                var themeMode by rememberStringSetting(
                    key = Constants.THEME_MODE_KEY,
                    defaultValue = ThemeMode.SYSTEM.name
                )
                val selectedTheme by remember {
                    derivedStateOf {
                        ThemeMode.entries.first { it.name == themeMode }
                    }
                }

                LaunchedEffect(Unit) {
                    Colors.setThemeMode(selectedTheme)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background.copy(alpha = 0f)
                ) {
                    val showDialog by viewModel.showDialog.collectAsState()
                    val convertedLinksState by viewModel.convertedLinksState.collectAsState()

                    LaunchedEffect(sharedText) {
                        if (sharedText != null) {
                            viewModel.convertLink(sharedText)
                        }
                    }

                    if (showDialog) {
                        ConvertedLinksDialog(
                            convertedLinksState = convertedLinksState,
                            onDismiss = {
                                viewModel.closeDialog()
                                finish()
                            }
                        )
                    }
                }
            }
        }
    }
}