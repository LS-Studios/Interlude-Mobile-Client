package de.stubbe.interlude.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stubbe.interlude.domain.model.ConvertedLink
import de.stubbe.interlude.domain.model.ConvertedLinksState
import de.stubbe.interlude.domain.model.HistoryItem
import de.stubbe.interlude.domain.model.Provider
import de.stubbe.interlude.domain.repository.CachedProviderRepository
import de.stubbe.interlude.domain.repository.HistoryRepository
import de.stubbe.interlude.domain.repository.InterludeNetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryScreenViewModel(
    private val interludeNetworkRepository: InterludeNetworkRepository,
    private val cachedProviderRepository: CachedProviderRepository,
    private val historyRepository: HistoryRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            interludeNetworkRepository.getAvailableProviders()
        }
    }

    val history: StateFlow<List<HistoryItem>> = combine(
       historyRepository.getAllHistories(),
        cachedProviderRepository. getCachedProvidersFlow()
    ) { histories, cachedProviders ->
        coroutineScope {
            val providers = cachedProviders.map { Provider.fromCached(it) }

            if (providers.isEmpty()) return@coroutineScope emptyList()

            histories.map { history ->
                async {
                    val songs = historyRepository.getSongsOfHistory(history.id)
                    val baseSong = ConvertedLink.fromEntity(providers, songs.find { it.id == history.baseConvertedLinkId }!!)
                    HistoryItem(history.id, baseSong, songs.map { ConvertedLink.fromEntity(providers, it) })
                }
            }
        }.awaitAll()
    }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _convertedLinksState: MutableStateFlow<ConvertedLinksState> = MutableStateFlow(ConvertedLinksState())
    val convertedLinksState: StateFlow<ConvertedLinksState> = _convertedLinksState

    private val _linkDialogIsOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val linkDialogIsOpen: StateFlow<Boolean> = _linkDialogIsOpen

    fun openHistoryItem(historyItem: HistoryItem) {
        _convertedLinksState.value = ConvertedLinksState(
            convertedLinks = historyItem.convertedLinks
        )
        _linkDialogIsOpen.value = true
    }

    fun closeLinkDialog() {
        _convertedLinksState.value = ConvertedLinksState()
        _linkDialogIsOpen.value = false
    }

    fun deleteHistoryItem(historyItem: HistoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.deleteHistoryItem(historyItem.id)
        }
    }

}