package de.stubbe.interlude.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stubbe.interlude.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val historyRepository: HistoryRepository
): ViewModel() {

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.clearHistory()
        }
    }

}