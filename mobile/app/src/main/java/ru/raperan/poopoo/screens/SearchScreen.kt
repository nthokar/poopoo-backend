package ru.raperan.poopoo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.navigation.NavController
import kotlinx.coroutines.flow.debounce
import ru.raperan.poopoo.elements.AlbumItem
import ru.raperan.poopoo.elements.ArtistItem
import ru.raperan.poopoo.elements.SearchBar
import ru.raperan.poopoo.elements.TrackItem
import ru.raperan.poopoo.viewmodels.FavoriteViewModel
import ru.raperan.poopoo.viewmodels.SearchViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    favoriteViewModel: FavoriteViewModel,
    navigationController: NavController // Передаем NavController
) {
    var query by remember { mutableStateOf("") }
    var debouncedQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("tracks") }

    LaunchedEffect(query) {
        snapshotFlow { query }
            .debounce(500)
            .collect { debouncedValue ->
                if (debouncedValue.isNotBlank()) {
                    debouncedQuery = debouncedValue
                    searchViewModel.autoComplete(debouncedQuery, selectedFilter)
                }
            }
    }

    Column {
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = { searchViewModel.autoComplete(debouncedQuery, selectedFilter) },
            placeholder = "Введите название трека",
            selectedFilter = selectedFilter,
            onFilterChange = { filter ->
                selectedFilter = filter
                searchViewModel.autoComplete(debouncedQuery, selectedFilter)
            }
        )

        when (selectedFilter) {
            "tracks" -> {
                LazyColumn {
                    items(searchViewModel.tracks) { track ->
                        TrackItem(
                            track = track,
                            onClick = {},
                            onFavoriteClick = {
                                track.isFavorite = !track.isFavorite
                                favoriteViewModel.setIsFavorite(track, track.isFavorite)
                            }
                        )
                    }
                }
            }
            "albums" -> {
                LazyColumn {
                    items(searchViewModel.albums) { album ->
                        AlbumItem(album = album, onClick = {
                            navigationController.navigate("albumDetails/${album.albumId}")
                        })
                    }
                }
            }
            "artists" -> {
                LazyColumn {
                    items(searchViewModel.artists) { artist ->
                        ArtistItem(artist = artist, onClick = {
                            // Логика по клику
                        })
                    }
                }
            }
        }
    }
}
