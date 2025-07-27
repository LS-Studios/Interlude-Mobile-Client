package de.stubbe.interlude.view.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.stubbe.interlude.model.ConvertedLink
import de.stubbe.interlude.model.ConvertedLinksState
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants
import de.stubbe.interlude.util.copySongToClipboard
import de.stubbe.interlude.util.getPlatformContext
import de.stubbe.interlude.util.shareSong
import de.stubbe.interlude.view.components.LoadingAsyncImage
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.ic_image_error
import kotlinx.coroutines.launch
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConvertedLinksDialog(
    convertedLinksState: ConvertedLinksState,
    onDismiss: () -> Unit = {}
) {
    val dialogState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val coroutineScope = rememberCoroutineScope()
    val context = getPlatformContext()

    val toastBGColor = Colors.Error
    val toastTextColor = Colors.OnError

    LaunchedEffect(convertedLinksState.errorMessage) {
        if (convertedLinksState.errorMessage == null) return@LaunchedEffect

        val errorMessage = "Service not available"

        showToast(
            message = errorMessage,
            backgroundColor = toastBGColor,
            textColor = toastTextColor,
            duration = ToastDuration.Short
        )
        onDismiss()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = dialogState,
        containerColor = Colors.Main,
        contentColor = Colors.Text,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Colors.Border) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Constants.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(Constants.SpacerLarge)
        ) {
            if (convertedLinksState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Constants.PaddingLarge),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Colors.Text
                    )
                }
            } else {
                val convertedLinks = remember(convertedLinksState) { convertedLinksState.convertedLinks }

                if (convertedLinks.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Click convert to see the results",
                        textAlign = TextAlign.Center,
                        color = Colors.Text,
                        fontSize = Constants.FontSizeMedium
                    )
                }

                convertedLinks.forEach { convertedLink ->
                    ConvertedLinkItem(
                        convertedLink = convertedLink
                    ) {
                        coroutineScope.launch {
                            copySongToClipboard(convertedLink, context)
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun ConvertedLinkItem(
    convertedLink: ConvertedLink,
    onClick: () -> Unit
) {
    val context = getPlatformContext()

    Row(
        modifier = Modifier
            .background(Colors.Main, Constants.Shape.Rounded.Small)
            .border(
                BorderStroke(Constants.BorderWidthSmall, Colors.Border),
                Constants.Shape.Rounded.Small
            )
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
            imageUrl = convertedLink.artwork,
            contentDescription = convertedLink.provider.name,
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
                text = convertedLink.displayName,
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
                    imageUrl = convertedLink.provider.iconUrl,
                    contentDescription = convertedLink.provider.name,
                    nonImageColor = Colors.Text,
                    error = painterResource(Res.drawable.ic_image_error),
                    alwaysUseColorFilter = true,
                    modifier = Modifier
                        .size(20.dp)
                )

                Text(
                    text = convertedLink.provider.getFormattedName(),
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
            onClick = {
                shareSong(convertedLink, context)
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send link",
            )
        }
    }
}