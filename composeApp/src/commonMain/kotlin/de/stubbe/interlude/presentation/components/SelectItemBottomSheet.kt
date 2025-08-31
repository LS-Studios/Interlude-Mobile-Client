package de.stubbe.interlude.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import de.stubbe.interlude.platform.ui.Constants
import de.stubbe.interlude.platform.ui.Colors
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SelectItemBottomSheet(
    show: Boolean,
    items: List<T>,
    selectedItem: T,
    getTitle: @Composable (T) -> String,
    onItemSelected: (T) -> Unit,
    onDismiss: () -> Unit,
) {
    if (!show) return

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val listState = rememberLazyListState()

    LaunchedEffect(selectedItem) {
        if (selectedItem == null) return@LaunchedEffect

        launch {
            items.indexOf(selectedItem).takeIf { it != -1 }?.let { index ->
                listState.animateScrollToItem(index + 1)
            }
        }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = Colors.Main,
    ) {
        LazyColumn(
            state = listState,
        ) {
            itemsIndexed(items) { index, item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemSelected(item)
                            onDismiss()
                        }
                        .padding(Constants.PaddingLarge),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = getTitle(item),
                        style = MaterialTheme.typography.titleMedium,
                        color = if (item == selectedItem) Colors.Accent else Colors.Text,
                    )
                    Icon(
                        imageVector = if (item == selectedItem) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Radio icon",
                        tint = if (item == selectedItem) Colors.Accent else Colors.Text
                    )
                }
                if (index != items.lastIndex) {
                    HorizontalDivider(color = Colors.Border)
                }
            }
        }
    }
}