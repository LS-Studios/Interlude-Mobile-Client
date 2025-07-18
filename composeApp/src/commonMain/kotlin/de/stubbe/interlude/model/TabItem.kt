package de.stubbe.interlude.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TabItem(
    val route: Route,
    val title: String,
    val icon: ImageVector
)
