package de.stubbe.interlude.presentation.components 

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import de.stubbe.interlude.ui.theme.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Colors.Background,
        ),
        title = {
            Text(
                text = title,
                color = Colors.Text,
                fontWeight = FontWeight.Black
            )
        }
    )
}