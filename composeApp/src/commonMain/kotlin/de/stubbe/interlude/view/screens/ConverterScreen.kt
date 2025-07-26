package de.stubbe.interlude.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.stubbe.interlude.model.Provider
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants
import de.stubbe.interlude.view.components.LoadingAsyncImage
import de.stubbe.interlude.view.components.NonlazyGrid
import de.stubbe.interlude.view.components.RoundedInputField
import de.stubbe.interlude.view.dialogs.ConvertedLinksDialog
import de.stubbe.interlude.view.dialogs.ErrorDialog
import de.stubbe.interlude.viewmodel.ConverterScreenViewModel
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.ic_image
import interlude.composeapp.generated.resources.ic_image_error
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConverterScreen() {
    val viewModel: ConverterScreenViewModel = koinViewModel()

    val link by viewModel.link.collectAsState()
    val availablePlatformsState by viewModel.availablePlatformsState.collectAsState()
    val convertedLinksState by viewModel.convertedLinksState.collectAsState()
    val linkDialogIsOpen by viewModel.linkDialogIsOpen.collectAsState()

    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.PaddingLarge)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Constants.SpacerXLarge)
    ) {
        RoundedInputField(
            modifier = Modifier
                .fillMaxWidth(),
            value = link,
            onValueChange = { newLink ->
                viewModel.setLink(newLink)
            },
            placeholder = "Past you music link here",
            maxLines = 4
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                viewModel.convertLink()
                viewModel.openLinkDialog()
            },
            shape = Constants.Shape.Rounded.Small,
            colors = ButtonDefaults.buttonColors(containerColor = Colors.Accent)
        ) {
            Text(
                modifier = Modifier
                    .padding(Constants.PaddingSmall),
                text = "Convert",
                color = Colors.Text,
                fontSize = Constants.FontSizeMedium
            )
        }

        SectionHeader("Available Platforms")

        if (availablePlatformsState.isLoading) {
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
            NonlazyGrid(
                columns = 2,
                itemCount = availablePlatformsState.providers.size,
                verticalItemPadding = Constants.SpacerLarge,
                horizontalItemPadding = Constants.SpacerLarge
            ) { platformIndex ->
                val platform = availablePlatformsState.providers[platformIndex]

                PlatformItem(
                    provider = platform
                ) {
                    uriHandler.openUri(platform.url)
                }
            }

            if (availablePlatformsState.errorMessage != null) {
                ErrorDialog(
                    message = availablePlatformsState.errorMessage!!,
                    confirmText = "Ok",
                    onDismiss = {

                    }
                )
            }
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
private fun SectionHeader(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = title,
        textAlign = TextAlign.Center,
        color = Colors.Text,
        fontSize = Constants.FontSizeXLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun PlatformItem(
    provider: Provider,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clickable(
                onClick = onClick,
                indication = ripple(),
                interactionSource = null
            )
            .fillMaxWidth()
            .background(Colors.Main, Constants.Shape.Rounded.Small)
            .border(
                BorderStroke(
                    Constants.BorderWidthSmall,
                    Colors.Border
                ),
                Constants.Shape.Rounded.Small
            )
            .padding(Constants.PaddingLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Constants.SpacerMedium, Alignment.CenterHorizontally)
    ) {
        LoadingAsyncImage(
            imageUrl = provider.iconUrl,
            contentDescription = provider.name,
            nonImageColor = Colors.Text,
            fallback = painterResource(Res.drawable.ic_image),
            error = painterResource(Res.drawable.ic_image_error),
            alwaysUseColorFilter = true,
            modifier = Modifier
                .size(30.dp)
        )

        Text(
            text = provider.getFormattedName(),
            fontSize = Constants.FontSizeMedium,
            color = Colors.Text,
        )
    }
}