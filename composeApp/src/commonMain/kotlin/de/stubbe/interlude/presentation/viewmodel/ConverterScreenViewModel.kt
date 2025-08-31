package de.stubbe.interlude.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stubbe.interlude.data.database.entities.ConvertedLinkEntity
import de.stubbe.interlude.domain.model.ProviderState
import de.stubbe.interlude.data.network.model.onError
import de.stubbe.interlude.data.network.model.onSuccess
import de.stubbe.interlude.domain.model.ConvertedLinksState
import de.stubbe.interlude.domain.model.Provider
import de.stubbe.interlude.domain.repository.CachedProviderRepository
import de.stubbe.interlude.domain.repository.HistoryRepository
import de.stubbe.interlude.domain.repository.InterludeNetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConverterScreenViewModel(
    private val interludeNetworkRepository: InterludeNetworkRepository,
    private val cachedProviderRepository: CachedProviderRepository,
    private val historyRepository: HistoryRepository,
): ViewModel() {

    private val _link: MutableStateFlow<String> = MutableStateFlow("")
    val link: StateFlow<String> = _link

    private val _convertedLinksState: MutableStateFlow<ConvertedLinksState> = MutableStateFlow(ConvertedLinksState())
    val convertedLinksState: StateFlow<ConvertedLinksState> = _convertedLinksState

    private val _linkDialogIsOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val linkDialogIsOpen: StateFlow<Boolean> = _linkDialogIsOpen

    init {
        viewModelScope.launch {
            interludeNetworkRepository.getAvailableProviders()
        }
    }

    val availableProviderState: StateFlow<ProviderState> = cachedProviderRepository.getCachedProvidersFlow()
        .map { cachedProviders ->
            if (cachedProviders.isEmpty()) {
                ProviderState(isLoading = true)
            } else {
                ProviderState(
                    providers = cachedProviders.map { Provider.fromCached(it) },
                    isLoading = false
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProviderState(isLoading = true)
        )

    fun setLink(link: String) {
        _link.value = link
    }

    fun convertLink() {
        _convertedLinksState.value = ConvertedLinksState(
            isLoading = true
        )

        viewModelScope.launch {
            interludeNetworkRepository.convert(
                link = _link.value,
            ).onSuccess { convertedLinks ->
                _convertedLinksState.value = ConvertedLinksState(
                    convertedLinks = convertedLinks
                )
                historyRepository.addHistory(_link.value, convertedLinks.map {
                    ConvertedLinkEntity.fromConvertedLink(it)
                })
            }.onError { error ->
                val errorMessage = error.message
                _convertedLinksState.value = ConvertedLinksState(
                    errorMessage = errorMessage
                )
            }
        }
    }

    fun openLinkDialog() {
        _linkDialogIsOpen.value = true
    }

    fun closeLinkDialog() {
        _linkDialogIsOpen.value = false
    }

}