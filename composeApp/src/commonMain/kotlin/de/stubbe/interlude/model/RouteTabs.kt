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

fun getRouteTabs() = listOf(
    TabItem(Route.Converter, "Converter", Icons.Default.SwapHoriz),
    TabItem(Route.History, "History", Icons.Default.History),
    TabItem(Route.Settings, "Settings", Icons.Default.Settings)
)