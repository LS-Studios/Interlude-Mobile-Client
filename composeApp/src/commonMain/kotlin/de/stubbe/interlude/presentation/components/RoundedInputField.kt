package de.stubbe.interlude.presentation.components 

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.stubbe.interlude.platform.ui.Colors
import de.stubbe.interlude.platform.ui.Constants

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