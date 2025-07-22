package de.stubbe.interlude.view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import de.stubbe.interlude.model.MenuItemModel
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants

@Composable
fun DropSelection(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onDismissRequest: () -> Unit,
    menuItems: List<MenuItemModel>,
    offset: DpOffset = DpOffset.Zero
) {
    DropdownMenu(
        modifier = modifier,
        expanded = visible,
        onDismissRequest = onDismissRequest,
        shape = Constants.Shape.Rounded.Small,
        containerColor = Colors.Main,
        offset = offset,
    ) {
        menuItems.forEachIndexed { index, menuItem ->
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = menuItem.title,
                        color = Colors.Text,
                        textAlign = TextAlign.Center
                    )
                },
                leadingIcon = {
                    if (menuItem.leadingIcon != null) {
                        Icon(
                            imageVector = menuItem.leadingIcon,
                            contentDescription = null,
                            tint = Colors.Text
                        )
                    }
                },
                trailingIcon = {
                    if (menuItem.trailingIcon != null) {
                        Icon(
                            imageVector = menuItem.trailingIcon,
                            contentDescription = null,
                            tint = Colors.Text
                        )
                    }
                },
                onClick = {
                    menuItem.onClick()
                    onDismissRequest()
                },
                contentPadding = PaddingValues(Constants.PaddingMedium)
            )

            if (index < menuItems.size - 1) {
                HorizontalDivider()
            }
        }
    }
}