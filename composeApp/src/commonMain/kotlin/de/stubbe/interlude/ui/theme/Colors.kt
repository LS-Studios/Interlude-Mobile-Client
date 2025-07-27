package de.stubbe.interlude.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import de.stubbe.interlude.model.ThemeMode

/**
 * Theme-aware color system with user preference support
 */
object Colors {
    private var currentThemeMode by mutableStateOf(ThemeMode.LIGHT)

    val Accent = Color(0xFF1A94E5)
    val OnAccent = Color(0xFFFFFFFF)
    val Error = Color(0xFFB00020)
    val OnError = Color(0xFFFFFFFF)
    
    /**
     * Determines if dark theme should be used based on preference
     */
    @Composable
    private fun shouldUseDarkTheme(): Boolean {
        return when (currentThemeMode) {
            ThemeMode.SYSTEM -> isSystemInDarkTheme()
            ThemeMode.LIGHT -> false
            ThemeMode.DARK -> true
        }
    }

    // Theme-dependent colors
    @Composable
    fun getMain(): Color = if (shouldUseDarkTheme()) Color(0xFF1C2126) else Color(0xFFedf0f6)

    @Composable
    fun getBackground(): Color = if (shouldUseDarkTheme()) Color(0xFF121417) else Color(0xFFf8fafc)

    @Composable
    fun getBorder(): Color = if (shouldUseDarkTheme()) Color(0xFF3D4A54) else Color(0xFFD1DEE8)

    @Composable
    fun getText(): Color = if (shouldUseDarkTheme()) Color(0xFFFFFFFF) else Color(0xFF0D171C)

    val Main @Composable get() = getMain()
    val Background @Composable get() = getBackground()
    val Border @Composable get() = getBorder()
    val Text @Composable get() = getText()
    val UnselectedText @Composable get() = Text.copy(alpha = 0.5f)
    
    @Composable
    fun getTextFieldColors() = TextFieldDefaults.colors(
        focusedTextColor = Text,
        unfocusedTextColor = Text.copy(alpha = 0.8f),
        disabledTextColor = UnselectedText.copy(alpha = 0.5f),
        errorTextColor = Error,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Background.copy(alpha = 0.3f),
        errorContainerColor = Error,
        cursorColor = Accent,
        errorCursorColor = Error,
        selectionColors = TextSelectionColors(
            handleColor = Accent,
            backgroundColor = Accent.copy(alpha = 0.4f)
        ),
        focusedIndicatorColor = Border,
        unfocusedIndicatorColor = Border,
        disabledIndicatorColor = Border.copy(alpha = 0.5f),
        errorIndicatorColor = Error,
        focusedLeadingIconColor = Text,
        unfocusedLeadingIconColor = Text.copy(alpha = 0.8f),
        disabledLeadingIconColor = UnselectedText.copy(alpha = 0.5f),
        errorLeadingIconColor = Error,
        focusedTrailingIconColor = Text,
        unfocusedTrailingIconColor = Text.copy(alpha = 0.8f),
        disabledTrailingIconColor = UnselectedText.copy(alpha = 0.5f),
        errorTrailingIconColor = Error,
        focusedLabelColor = Text.copy(alpha = 0.8f),
        unfocusedLabelColor = Text.copy(alpha = 0.6f),
        disabledLabelColor = UnselectedText.copy(alpha = 0.5f),
        errorLabelColor = Error,
        focusedPlaceholderColor = UnselectedText.copy(alpha = 0.8f),
        unfocusedPlaceholderColor = UnselectedText.copy(alpha = 0.6f),
        disabledPlaceholderColor = UnselectedText.copy(alpha = 0.5f),
        errorPlaceholderColor = Error,
        focusedSupportingTextColor = UnselectedText.copy(alpha = 0.8f),
        unfocusedSupportingTextColor = UnselectedText.copy(alpha = 0.6f),
        disabledSupportingTextColor = UnselectedText.copy(alpha = 0.5f),
        errorSupportingTextColor = Error,
        focusedPrefixColor = Text,
        unfocusedPrefixColor = Text.copy(alpha = 0.8f),
        disabledPrefixColor = UnselectedText.copy(alpha = 0.5f),
        errorPrefixColor = Error,
        focusedSuffixColor = Text,
        unfocusedSuffixColor = Text.copy(alpha = 0.8f),
        disabledSuffixColor = UnselectedText.copy(alpha = 0.5f),
        errorSuffixColor = Error
    )

    /**
     * Set theme mode preference
     */
    fun setThemeMode(mode: ThemeMode) {
        currentThemeMode = mode
    }
}