package de.stubbe.interlude.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.app_name
import interlude.composeapp.generated.resources.converter
import interlude.composeapp.generated.resources.history
import interlude.composeapp.generated.resources.settings
import org.jetbrains.compose.resources.stringResource

@Composable
fun getRouteTabs() = listOf(
    TabItem(Route.Converter, Icons.Default.SwapHoriz),
    TabItem(Route.History, Icons.Default.History),
    TabItem(Route.Settings, Icons.Default.Settings)
)