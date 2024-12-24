package ru.raperan.poopoo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.session.MediaController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.raperan.poopoo.MainActivity
import ru.raperan.poopoo.api.PlayService
import java.util.stream.Collectors

class PlayBarViewModel(
    val playService: PlayService,
    val controller: MediaController

) : ViewModel() {
//    var controller: MediaController? = null

    var currentProgress = 0f
        private set // Закрытый сеттер, чтобы значение не менялось напрямую

    var isPlaying = false
        private set // Закрытый сеттер для управления воспроизведением через методы


    fun start(updateProgress: (Float) -> Unit) {
        viewModelScope.launch {
            loadProgress(updateProgress)
        }
    }

    /** Iterate the progress value */
    suspend fun loadProgress(updateProgress: (Float) -> Unit) {
        //suspend fun loadProgress(updateProgress: (Float) -> Unit, progressFetcher: () -> Float, controller: MediaController) {
        while (true) {
            currentProgress =
                if (this.controller != null) this.controller!!.currentPosition.toFloat() / this.controller!!.contentDuration.toFloat() else 0F
            updateProgress(currentProgress)
            delay(100)
        }
    }



    /** Переключение состояния воспроизведения */
    fun togglePlayPause(play: Boolean) {
        isPlaying = play
        controller?.let {
            if (isPlaying) {
                it.play()
            } else {
                it.pause()
            }
        }
    }

    fun getTrackTitle(): String? {
        return playService.currentTrack?.name
    }

    fun getArtistName(): String? {
        return playService.currentTrack?.authorName
    }

    fun getAlbumName(): String? {
        return ""
    }

    fun playNextTrack() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = playService.playNextTrack() // fetchData is a suspend function
            withContext(Dispatchers.Main) {
                if (response != null) {
                    val mediaItem =
                        MediaItem.Builder()
                            .setMediaId("media-1")
                            .setUri(MainActivity.API_URL + response.fileUrl)
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
}

class PlayBarViewModelFactory(
    private val playService: PlayService,
    private val controller: MediaController
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlayBarViewModel::class.java)) {
            return PlayBarViewModel(playService, controller) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}