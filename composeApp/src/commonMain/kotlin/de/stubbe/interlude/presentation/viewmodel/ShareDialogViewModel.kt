package de.stubbe.interlude.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stubbe.interlude.data.database.entities.ConvertedLinkEntity
import de.stubbe.interlude.data.network.model.onError
import de.stubbe.interlude.data.network.model.onSuccess
import de.stubbe.interlude.domain.model.ConvertedLinksState
import de.stubbe.interlude.domain.repository.HistoryRepository
import de.stubbe.interlude.domain.repository.InterludeNetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShareDialogViewModel(
    private val interludeNetworkRepository: InterludeNetworkRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {

    private val _convertedLinksState: MutableStateFlow<ConvertedLinksState> = MutableStateFlow(ConvertedLinksState())
    val convertedLinksState: StateFlow<ConvertedLinksState> = _convertedLinksState

    private val _showDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    fun convertLink(link: String) {
        _convertedLinksState.value = ConvertedLinksState(isLoading = true)
        _showDialog.value = true

        viewModelScope.launch {
            interludeNetworkRepository.convert(link = link)
                .onSuccess { convertedLinks ->
                    _convertedLinksState.value = ConvertedLinksState(
                        convertedLinks = convertedLinks
                    )
                    historyRepository.addHistory(link, convertedLinks.map {
                        ConvertedLinkEntity.fromConvertedLink(it)
                    })
                }
                .onError { error ->
                    _convertedLinksState.value = ConvertedLinksState(
                        errorMessage = error.message
                    )
                }
        }
    }

    fun closeDialog() {
        _showDialog.value = false
    }
}