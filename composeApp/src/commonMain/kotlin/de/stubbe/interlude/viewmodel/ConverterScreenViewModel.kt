package de.stubbe.interlude.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stubbe.interlude.database.entities.ConvertedLinkEntity
import de.stubbe.interlude.model.ConvertedLinksState
import de.stubbe.interlude.model.PlatformState
import de.stubbe.interlude.network.model.DataError
import de.stubbe.interlude.network.model.onError
import de.stubbe.interlude.network.model.onSuccess
import de.stubbe.interlude.repository.AppShareRepository
import de.stubbe.interlude.repository.HistoryRepository
import de.stubbe.interlude.repository.InterludeNetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ConverterScreenViewModel(
    private val interludeNetworkRepository: InterludeNetworkRepository,
    private val historyRepository: HistoryRepository,
    private val appShareRepository: AppShareRepository
): ViewModel() {

    private val _link: MutableStateFlow<String> = MutableStateFlow("")
    val link: StateFlow<String> = _link

    private val _convertedLinksState: MutableStateFlow<ConvertedLinksState> = MutableStateFlow(ConvertedLinksState())
    val convertedLinksState: StateFlow<ConvertedLinksState> = _convertedLinksState

    private val _linkDialogIsOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val linkDialogIsOpen: StateFlow<Boolean> = _linkDialogIsOpen

    init {
        viewModelScope.launch {
            appShareRepository.injectedLink
                .collect { sharedLink ->
                    if (sharedLink == null || sharedLink.isBlank()) return@collect

                    _link.value = sharedLink
                    convertLink()
                    openLinkDialog()
                }
        }
    }

    val availablePlatformsState: StateFlow<PlatformState> = flow {
        emit(PlatformState(isLoading = true))

        interludeNetworkRepository.getAvailablePlatforms()
            .onSuccess { result ->
                emit(
                    PlatformState(
                        providers = result,
                        isLoading = false
                    )
                )
            }
            .onError { error ->
                emit(
                    PlatformState(
                        errorMessage = error.message,
                        isLoading = false
                    )
                )
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlatformState(isLoading = true)
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