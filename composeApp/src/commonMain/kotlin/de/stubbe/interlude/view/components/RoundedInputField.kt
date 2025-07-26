package de.stubbe.interlude.view.components

import androidx.compose.animation.core.copy
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.Placeholder
import de.stubbe.interlude.ui.theme.Colors
import de.stubbe.interlude.ui.theme.Constants

@Composable
fun RoundedInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    val unselectedColor = Colors.UnselectedText

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = unselectedColor
            )
        },
        colors = Colors.getTextFieldColors(),
        shape = Constants.Shape.Rounded.Small,
        maxLines = maxLines,
    )
}