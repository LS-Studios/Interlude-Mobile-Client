package de.stubbe.interlude.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import org.koin.compose.koinInject

@Composable
actual fun getPlatformContext(): Any? = LocalContext.current