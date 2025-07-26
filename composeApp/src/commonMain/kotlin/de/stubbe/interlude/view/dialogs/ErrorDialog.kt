package de.stubbe.interlude.view.dialogs

import androidx.compose.runtime.Composable
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants

@Composable
fun ErrorDialog(
    title: String = "Error",
    message: String,
    confirmText: String = "OK",
    onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                androidx.compose.material3.Text(
                    text = confirmText,
                    color = Colors.Accent,
                    fontSize = Constants.FontSizeMedium
                )
            }
        },
        title = {
            androidx.compose.material3.Text(
                text = title,
                color = Colors.Error,
                fontSize = Constants.FontSizeLarge
            )
        },
        text = {
            androidx.compose.material3.Text(
                text = message,
                color = Colors.Text,
                fontSize = Constants.FontSizeMedium
            )
        },
        containerColor = Colors.Background,
        shape = Constants.Shape.Rounded.Medium,
        tonalElevation = Constants.ElevationMedium
    )
}