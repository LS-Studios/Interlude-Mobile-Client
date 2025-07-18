package de.stubbe.interlude.viewmodel

import androidx.lifecycle.ViewModel
import de.stubbe.interlude.model.ConvertedLink
import de.stubbe.interlude.model.Platform
import interlude.composeapp.generated.resources.Res
import interlude.composeapp.generated.resources.apple
import interlude.composeapp.generated.resources.deezer
import interlude.composeapp.generated.resources.soundcloud
import interlude.composeapp.generated.resources.spotify
import interlude.composeapp.generated.resources.tidal
import interlude.composeapp.generated.resources.youtube
import interlude.composeapp.generated.resources.youtube_music
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConverterScreenViewModel: ViewModel() {

    private val _link: MutableStateFlow<String> = MutableStateFlow("")
    val link: StateFlow<String> = _link

    private val _convertedLinks: MutableStateFlow<List<ConvertedLink>> = MutableStateFlow(emptyList())
    val convertedLinks: StateFlow<List<ConvertedLink>> = _convertedLinks

    private val _linkDialogIsOpen: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val linkDialogIsOpen: StateFlow<Boolean> = _linkDialogIsOpen

    private val _availablePlatforms: MutableStateFlow<List<Platform>> = MutableStateFlow(listOf(
        Platform(
            "sy",
            "https://open.spotify.com",
            "Spotify",
            "",
            Res.drawable.spotify
        ),
        Platform(
            "am",
            "https://music.apple.com",
            "Apple Music",
            "",
            Res.drawable.apple
        ),
        Platform(
            "yt",
            "https://youtube.com/",
            "YouTube",
            "",
            Res.drawable.youtube
        ),
        Platform(
            "ytm",
            "https://music.youtube.com/",
            "YouTube Music",
            "",
            Res.drawable.youtube_music
        ),
        Platform(
            "dr",
            "https://www.deezer.com",
            "Deezer",
            "",
            Res.drawable.deezer
        ),
        Platform(
            "sc",
            "https://soundcloud.com",
            "Sound Cloud",
            "",
            Res.drawable.soundcloud
        ),
        Platform(
            "tl",
            "https://tidal.com/",
            "Tidal",
            "",
            Res.drawable.tidal
        )
    ))
    val availablePlatforms: StateFlow<List<Platform>> = _availablePlatforms

    fun setLink(link: String) {
        _link.value = link
    }

    fun convertLink() {
        _convertedLinks.value = emptyList()

        // Load data

        _convertedLinks.value = listOf(
            ConvertedLink(
                link = "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
                songName = "Rick Astley - Never Gonna Give You Up (Official Music Video)",
                songImageUrl = "https://i.ytimg.com/vi/dQw4w9WgXcQ/maxresdefault.jpg",
                platform = _availablePlatforms.value.find { it.uid == "yt" }!!
            ),
            ConvertedLink(
                link = "https://music.apple.com/us/song/never-gonna-give-you-up/1452434833",
                songName = "Never Gonna Give You Up",
                songImageUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music124/v4/0d/5b/54/0d5b54cd-f0bf-7db0-52d2-3df5e1ddd10e/886447520017.jpg/416x416bb.webp",
                platform = _availablePlatforms.value.find { it.uid == "am" }!!
            ),
            ConvertedLink(
                link = "https://open.spotify.com/intl-de/track/4PTG3Z6ehGkBFwjybzWkR8?si=004c555778164ca0",
                songName = "Never Gonna Give You Up",
                songImageUrl = "https://i.scdn.co/image/ab67616d0000b273fbb5b8cc8a8bbd354eb1df6c",
                platform =  _availablePlatforms.value.find { it.uid == "sy" }!!
            ),
            ConvertedLink(
                link = "https://www.deezer.com/en/track/781592622",
                songName = "Never Gonna Give You Up",
                songImageUrl = "https://cdn-images.dzcdn.net/images/cover/fe779e632872f7c6e9f1c84ffa7afc33/500x500-000000-80-0-0.jpg",
                platform =  _availablePlatforms.value.find { it.uid == "dr" }!!
            ),
            ConvertedLink(
                link = "https://soundcloud.com/rick-astley-official/never-gonna-give-you-up",
                songName = "Never Gonna Give You Up",
                songImageUrl = "https://i1.sndcdn.com/artworks-M6uzzgisxFFC-0-t1080x1080.jpg",
                platform =  _availablePlatforms.value.find { it.uid == "sc" }!!
            ),
            ConvertedLink(
                link = "https://listen.tidal.com/track/101982419",
                songName = "Never Gonna Give You Up",
                songImageUrl = "https://resources.tidal.com/images/572a9272/5074/417c/920e/25e031c0572d/640x640.jpg",
                platform =  _availablePlatforms.value.find { it.uid == "tl" }!!
            )
        )
    }

    fun openLinkDialog() {
        _linkDialogIsOpen.value = true
    }

    fun closeLinkDialog() {
        _linkDialogIsOpen.value = false
    }

}