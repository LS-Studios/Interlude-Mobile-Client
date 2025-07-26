package de.stubbe.interlude.viewmodel

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.BuiltInTypeConverters
import de.stubbe.interlude.model.Language
import de.stubbe.interlude.model.ThemeMode
import de.stubbe.interlude.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val historyRepository: HistoryRepository
): ViewModel() {

    private val _selectedTheme: MutableStateFlow<ThemeMode> = MutableStateFlow(ThemeMode.LIGHT)
    val selectedTheme: StateFlow<ThemeMode> = _selectedTheme

    private val _selectedLanguage: MutableStateFlow<Language> = MutableStateFlow(Language.ENGLISH)
    val selectedLanguage: StateFlow<Language> = _selectedLanguage

    private val _historyEnabled: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val historyEnabled: StateFlow<Boolean> = _historyEnabled

    fun setTheme(theme: ThemeMode) {
        _selectedTheme.value = theme
    }

    fun setLanguage(language: Language) {
        _selectedLanguage.value = language
    }

    fun setHistoryEnabled(enabled: Boolean) {
        _historyEnabled.value = enabled
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.clearHistory()
        }
    }

}