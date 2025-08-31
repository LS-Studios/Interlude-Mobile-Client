package de.stubbe.interlude.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.stubbe.interlude.domain.model.HistoryItem
import de.stubbe.interlude.presentation.components.LoadingAsyncImage
import de.stubbe.interlude.presentation.viewmodel.HistoryScreenViewModel
import de.stubbe.interlude.platform.ui.Colors
import de.stubbe.interlude.platform.ui.Constants
import de.stubbe.interlude.presentation.dialogs.ConvertedLinksDialog
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.delete_history
import interlude.composeapp.generated.resources.delete_history_item
import interlude.composeapp.generated.resources.ic_image_error
import interlude.composeapp.generated.resources.no_history_yet
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HistoryScreen() {
    val viewModel: HistoryScreenViewModel = koinViewModel()

    val history by viewModel.history.collectAsState()
    val convertedLinksState by viewModel.convertedLinksState.collectAsState()
    val linkDialogIsOpen by viewModel.linkDialogIsOpen.collectAsState()

    val toastBGColor = Colors.Accent
    val toastTextColor = Colors.OnAccent

    val noHistoryText = stringResource(Res.string.no_history_yet)
    val deleteHistoryText = stringResource(Res.string.delete_history)

    LazyColumn(
        modifier = Modifier
            .padding(Constants.PaddingSmall),
        verticalArrangement = Arrangement.spacedBy(Constants.SpacerSmall)
    ) {
        item {
            if (history.isEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = noHistoryText,
                    color = Colors.Text,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    fontSize = Constants.FontSizeMedium
                )
            }
        }
        items(history) { historyItem ->
            HistoryRow(
                historyItem = historyItem,
                onClick = {
                    viewModel.openHistoryItem(historyItem)
                },
                onDelete = {
                    viewModel.deleteHistoryItem(historyItem)
                    showToast(
                        message = deleteHistoryText,
                        backgroundColor = toastBGColor,
                        textColor = toastTextColor,
                        duration = ToastDuration.Short
                    )
                }
            )
        }
    }

    if (linkDialogIsOpen) {
        ConvertedLinksDialog(
            convertedLinksState = convertedLinksState,
            onDismiss =  {
                viewModel.closeLinkDialog()
            }
        )
    }
}

@Composable
private fun HistoryRow(
    historyItem: HistoryItem,
    onClick: () -> Unit = {},
    onDelete: () -> Unit = {}
) {
    val deletedHistoryItemText = stringResource(Res.string.delete_history_item)

    Row(
        modifier = Modifier
            .clickable(
                interactionSource = null,
                indication = ripple()
            ) {
                onClick()
            }
            .padding(Constants.PaddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Constants.SpacerLarge)
    ) {
        LoadingAsyncImage(
            imageUrl = historyItem.baseSong.artwork,
            contentDescription = historyItem.baseSong.provider.name,
            nonImageColor = Colors.Text,
            error = painterResource(Res.drawable.ic_image_error),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(Constants.Shape.Rounded.Small)
        )

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = historyItem.baseSong.displayName,
                fontSize = Constants.FontSizeMedium,
                color = Colors.Text,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                softWrap = false
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Constants.SpacerMedium)
            ) {
                LoadingAsyncImage(
                    imageUrl = historyItem.baseSong.provider.iconUrl,
                    contentDescription = historyItem.baseSong.provider.name,
                    nonImageColor = Colors.Text,
                    error = painterResource(Res.drawable.ic_image_error),
                    alwaysUseColorFilter = true,
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = historyItem.baseSong.provider.getFormattedName(),
                    fontSize = Constants.FontSizeMedium,
                    color = Colors.Text,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    fontWeight = FontWeight.Light
                )
            }
        }

        IconButton(
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = deletedHistoryItemText,
                tint = Colors.Text
            )
        }
    }
}