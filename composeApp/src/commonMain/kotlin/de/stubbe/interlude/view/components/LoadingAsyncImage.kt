package de.stubbe.interlude.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.ic_image
import interlude.composeapp.generated.resources.ic_image_error
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoadingAsyncImage(
    imageUrl: String,
    contentDescription: String,
    nonImageColor: Color = Color.White,
    fallback: Painter? = null,
    error: Painter? = null,
    contentScale: ContentScale = ContentScale.Fit,
    alwaysUseColorFilter: Boolean = false,
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf<AsyncImagePainter.State?>(null) }
    val painter = rememberAsyncImagePainter(imageUrl, onState = { newState ->
        state = newState
    })

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (state) {
            is AsyncImagePainter.State.Loading,
            is AsyncImagePainter.State.Empty -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Constants.PaddingMedium),
                    color = nonImageColor
                )
            }

            else -> {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = contentDescription,
                    fallback = fallback,
                    error = error,
                    placeholder = painterResource(Res.drawable.ic_image),
                    contentScale = contentScale,
                    colorFilter = if (alwaysUseColorFilter)
                            ColorFilter.tint(nonImageColor)
                        else when (state) {
                            is AsyncImagePainter.State.Success -> null
                            else -> ColorFilter.tint(nonImageColor)
                    },
                )
            }
        }
    }
}