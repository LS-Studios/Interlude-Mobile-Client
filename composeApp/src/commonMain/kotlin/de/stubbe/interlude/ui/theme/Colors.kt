package de.stubbe.interlude.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

/**
 * Theme-aware color system with user preference support
 */
object Colors {
    private var currentThemeMode by mutableStateOf(ThemeMode.LIGHT)

    val Accent = Color(0xFF1A94E5)
    val SelectionColor = TextSelectionColors(Accent, Accent.copy(alpha = 0.5f))

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
    fun getMain(): Color = if (shouldUseDarkTheme()) Color(0xFF1C2126) else Color(0xFFf8fafc)
    
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

    /**
     * Set theme mode preference
     */
    fun setThemeMode(mode: ThemeMode) {
        currentThemeMode = mode
    }
}