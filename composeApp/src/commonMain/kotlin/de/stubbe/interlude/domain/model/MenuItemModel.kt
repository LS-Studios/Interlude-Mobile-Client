package de.stubbe.interlude.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemModel(
    val title: String,
    val onClick: () -> Unit,
    val leadingIcon: ImageVector? = null,
    val trailingIcon: ImageVector? = null
)