package de.stubbe.interlude.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable

@Composable
fun getRouteTabs() = listOf(
    TabItem(Route.Converter, Icons.Default.SwapHoriz),
    TabItem(Route.History, Icons.Default.History),
    TabItem(Route.Settings, Icons.Default.Settings)
)