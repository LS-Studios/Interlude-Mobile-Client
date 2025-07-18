package de.stubbe.interlude.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import de.stubbe.interlude.model.Platform
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants
import de.stubbe.interlude.view.components.LoadingAsyncImage
import de.stubbe.interlude.view.components.NonlazyGrid
import de.stubbe.interlude.view.components.RoundedInputField
import de.stubbe.interlude.view.dialogs.ConvertedLinksDialog
import de.stubbe.interlude.viewmodel.ConverterScreenViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConverterScreen() {
    val viewModel: ConverterScreenViewModel = koinViewModel()

    val link by viewModel.link.collectAsState()
    val availablePlatforms by viewModel.availablePlatforms.collectAsState()
    val convertedLinks by viewModel.convertedLinks.collectAsState()
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.Accent
            )
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

        NonlazyGrid(
            columns = 2,
            itemCount = availablePlatforms.size,
            verticalItemPadding = Constants.SpacerLarge,
            horizontalItemPadding = Constants.SpacerLarge
        ) { platformIndex ->
            val platform = availablePlatforms[platformIndex]

            PlatformItem(
                platform = platform
            ) {
                uriHandler.openUri(platform.url)
            }
        }
    }

    if (linkDialogIsOpen) {
        ConvertedLinksDialog(
            convertedLinks = convertedLinks,
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
    platform: Platform,
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
            imageUrl = platform.iconUrl,
            contentDescription = platform.name,
            nonImageColor = Colors.Text,
            fallback = painterResource(platform.fallbackResource),
            error = painterResource(platform.fallbackResource),
            modifier = Modifier
                .size(30.dp)
        )

        Text(
            text = platform.name,
            fontSize = Constants.FontSizeMedium,
            color = Colors.Text,
        )
    }
}