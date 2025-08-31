package de.stubbe.interlude.presentation.screens

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
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.stubbe.interlude.domain.model.Provider
import de.stubbe.interlude.platform.ui.Colors
import de.stubbe.interlude.platform.ui.Constants
import de.stubbe.interlude.presentation.components.LoadingAsyncImage
import de.stubbe.interlude.presentation.components.NonlazyGrid
import de.stubbe.interlude.presentation.components.RoundedInputField
import de.stubbe.interlude.presentation.dialogs.ConvertedLinksDialog
import de.stubbe.interlude.presentation.viewmodel.ConverterScreenViewModel
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.available_providers
import interlude.composeapp.generated.resources.convert
import interlude.composeapp.generated.resources.ic_image
import interlude.composeapp.generated.resources.ic_image_error
import interlude.composeapp.generated.resources.paste_music_link
import interlude.composeapp.generated.resources.service_not_available
import multiplatform.network.cmptoast.ToastDuration
import multiplatform.network.cmptoast.showToast
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ConverterScreen() {
    val viewModel: ConverterScreenViewModel = koinViewModel()

    val link by viewModel.link.collectAsState()
    val availableProviderState by viewModel.availableProviderState.collectAsState()
    val convertedLinksState by viewModel.convertedLinksState.collectAsState()
    val linkDialogIsOpen by viewModel.linkDialogIsOpen.collectAsState()

    val uriHandler = LocalUriHandler.current

    val toastBGColor = Colors.Error
    val toastTextColor = Colors.OnError

    val serviceNotAvailableText = stringResource(Res.string.service_not_available)
    val pastMusicLinkText = stringResource(Res.string.paste_music_link)
    val convertText = stringResource(Res.string.convert)
    val availableProvidersText = stringResource(Res.string.available_providers)

    LaunchedEffect(availableProviderState.errorMessage) {
        if (availableProviderState.errorMessage == null) return@LaunchedEffect

        val errorMessage = serviceNotAvailableText

        showToast(
            message = errorMessage,
            backgroundColor = toastBGColor,
            textColor = toastTextColor,
            duration = ToastDuration.Short
        )
    }

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
            placeholder = pastMusicLinkText,
            maxLines = 4
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                viewModel.convertLink()
                viewModel.openLinkDialog()
            },
            enabled = link.isNotBlank(),
            shape = Constants.Shape.Rounded.Small,
            colors = ButtonDefaults.buttonColors(
                containerColor = Colors.Accent,
                disabledContainerColor = Colors.Accent.copy(alpha = 0.5f),
                contentColor = Colors.OnAccent,
                disabledContentColor = Colors.OnAccent.copy(alpha = 0.5f)
            )
        ) {
            Text(
                modifier = Modifier
                    .padding(Constants.PaddingSmall),
                text = convertText,
                fontSize = Constants.FontSizeMedium
            )
        }

        SectionHeader(availableProvidersText)

        if (availableProviderState.isLoading) {
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
                itemCount = availableProviderState.providers.size,
                verticalItemPadding = Constants.SpacerLarge,
                horizontalItemPadding = Constants.SpacerLarge
            ) { providerIndex ->
                val provider = availableProviderState.providers[providerIndex]

                ProviderItem(
                    provider = provider
                ) {
                    uriHandler.openUri(provider.url)
                }
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
private fun ProviderItem(
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
            imageUrl = provider.logoUrl,
            contentDescription = provider.name,
            nonImageColor = Colors.Text,
            fallback = painterResource(Res.drawable.ic_image),
            error = painterResource(Res.drawable.ic_image_error),
            alwaysUseColorFilter = true,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(0.7f)
        )
    }
}