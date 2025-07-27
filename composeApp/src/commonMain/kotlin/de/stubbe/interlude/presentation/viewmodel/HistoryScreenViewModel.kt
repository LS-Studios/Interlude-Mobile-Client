package de.stubbe.interlude.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stubbe.interlude.data.network.model.component1
import de.stubbe.interlude.data.network.model.component2
import de.stubbe.interlude.domain.model.ConvertedLink
import de.stubbe.interlude.domain.model.HistoryItem
import de.stubbe.interlude.domain.repository.HistoryRepository
import de.stubbe.interlude.domain.repository.InterludeNetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val interludeNetworkRepository: InterludeNetworkRepository,
    private val historyRepository: HistoryRepository
): ViewModel() {

    val history: StateFlow<List<HistoryItem>> = historyRepository
        .getAllHistories()
        .map { histories ->
            coroutineScope {
                val (platforms, error) = interludeNetworkRepository.getAvailablePlatforms()

                histories.map { history ->
                    async {
                        val songs = historyRepository.getSongsOfHistory(history.id)
                        val baseSong = ConvertedLink.fromEntity(platforms!!, songs.find { it.id == history.baseConvertedLinkId }!!)
                        HistoryItem(history.id, baseSong, songs.map { ConvertedLink.fromEntity(platforms, it) })
                    }
                }
            }.awaitAll()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteHistoryItem(historyItem: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.deleteHistoryItem(historyItem.id)
        }
    }

}