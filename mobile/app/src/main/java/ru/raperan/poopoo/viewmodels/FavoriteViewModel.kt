package ru.raperan.poopoo.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.raperan.poopoo.MainActivity
import ru.raperan.poopoo.api.PlayService
import ru.raperan.poopoo.data.Track
import java.util.UUID
import java.util.stream.Collectors

class FavoriteViewModel(
    val playService: PlayService,
    val controller: MediaController
) : ViewModel() {
    val tracks = mutableStateListOf<Track>()
    var scrollPosition = 0

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val music = playService.getFavoriteTracks() // fetchData is a suspend function
            withContext(Dispatchers.Main) {
                if (music != null) {
                    music.forEach({m -> tracks.add(Track(UUID.fromString(m.trackId), m.poster, m.name, m.authorName, m.isFavorite))})
                }
            }
        }
    }

    fun playFavoriteFromTrack(track: Track) {
        GlobalScope.launch(Dispatchers.IO) {
            val music = playService.playFavoriteFromTrack(track) // fetchData is a suspend function
            withContext(Dispatchers.Main) {
                if (music != null) {
                    val mediaItem =
                        MediaItem.Builder()
                            .setMediaId("media-1")
                            .setUri(MainActivity.API_URL + music.fileUrl)
                            .setMediaMetadata(
                                MediaMetadata.Builder()
                                    .setArtist("David Bowie")
                                    .setTitle("Heroes")
                                    .build()
                            )
                            .build()

                    controller.setMediaItem(mediaItem)
                    controller.prepare()
                    controller.play()
                }
            }
        }
    }

    fun setIsFavorite(track: Track, isFavorite: Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            val response = playService.setIsFavorite(track.trackId.toString(), isFavorite) // fetchData is a suspend function
            if (isFavorite && !tracks.contains(track)) {
                val tmp = tracks.stream().collect(Collectors.toList())
                tracks.clear()

                tracks.addAll(listOf(track) + tmp)
            }
            withContext(Dispatchers.Main) {
            }
        }
    }

}

class FavoriteViewModelFactory(
    private val playService: PlayService,
    private val controller: MediaController
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(playService, controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}