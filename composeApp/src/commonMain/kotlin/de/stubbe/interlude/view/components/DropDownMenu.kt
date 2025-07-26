package de.stubbe.interlude.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownMenu(
    items: List<T>,
    getTitle: @Composable (T) -> String,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = Modifier.fillMaxWidth(),
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
            value = getTitle(selectedItem),
            onValueChange = {},
            shape = if (isExpanded) Constants.Shape.RoundedTop.Small else Constants.Shape.Rounded.Small,
            readOnly = true,
            textStyle = TextStyle(
                color = Colors.Text,
                textAlign = TextAlign.Center,
            ),
            colors = Colors.getTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            border = BorderStroke(Constants.BorderWidthSmall, Colors.Border),
            shape = if (isExpanded) Constants.Shape.RoundedBottom.Small else Constants.Shape.Rounded.Small,
            containerColor = Colors.Main
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = getTitle(item),
                            textAlign = TextAlign.Center,
                            color = Colors.Text
                        )
                    },
                    onClick = {
                        onItemSelected(item)
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
                if (index < items.size - 1) {
                    HorizontalDivider(
                        color = Colors.Border
                    )
                }
            }
        }
    }
}