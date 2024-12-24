package ru.raperan.poopoo.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.raperan.poopoo.api.AlbumBaseInfoDto
import ru.raperan.poopoo.api.ArtistBaseInfoDto
import ru.raperan.poopoo.api.TrackBaseInfoDto
import ru.raperan.poopoo.api.PlayService
import ru.raperan.poopoo.data.Album
import ru.raperan.poopoo.data.Artist
import ru.raperan.poopoo.data.Track
import java.util.UUID

class SearchViewModel(
    val playService: PlayService
) : ViewModel() {

    val tracks = mutableStateListOf<Track>()
    val albums = mutableStateListOf<Album>()
    val artists = mutableStateListOf<Artist>()
    var detailsAlbum: AlbumBaseInfoDto? = null

    fun autoComplete(query: String, filter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = playService.fetchAutocomplete(query, filter)

                withContext(Dispatchers.Main) {
                    when (filter) {
                        "tracks" -> {
                            tracks.clear()
                            tracks.addAll(
                                (result as List<TrackBaseInfoDto>).map {
                                    Track(UUID.fromString(it.trackId), it.poster, it.name, it.authorName, it.isFavorite)
                                }
                            )
                        }
                        "albums" -> {
                            albums.clear()
                            albums.addAll(
                                (result as List<AlbumBaseInfoDto>).map {
                                    Album(UUID.fromString(it.id), it.name, it.poster)
                                }
                            )
                        }
                        "artists" -> {
                            artists.clear()
                            artists.addAll(
                                (result as List<ArtistBaseInfoDto>).map {
                                    Artist(UUID.fromString(it.id), it.name)
                                }
                            )
                        }
                        else -> throw IllegalArgumentException("Unknown filter: $filter")
                    }
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error fetching autocomplete: ${e.message}")
            }
        }
    }

    fun getAlbumById(albumId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = playService.findAlbum(albumId)

                withContext(Dispatchers.Main) {
                    detailsAlbum = result
                }
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Error fetching album details: ${e.message}")
            }
        }
    }
}


class SearchViewModelFactory(
    private val playService: PlayService,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(playService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}