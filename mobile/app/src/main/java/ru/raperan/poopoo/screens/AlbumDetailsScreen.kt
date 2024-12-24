package ru.raperan.poopoo.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.raperan.poopoo.MainActivity
import ru.raperan.poopoo.data.Track
import ru.raperan.poopoo.elements.TrackItem
import ru.raperan.poopoo.viewmodels.FavoriteViewModel
import ru.raperan.poopoo.viewmodels.SearchViewModel
import java.util.UUID

@Composable
fun AlbumDetailsScreen(
    albumId: String,
    searchViewModel: SearchViewModel,
    favoriteViewModel: FavoriteViewModel
) {
    // Состояние для отслеживания загрузки альбома
    val isLoading = remember { mutableStateOf(true) }

    // Запрашиваем альбом по ID
    LaunchedEffect(albumId) {
        // Устанавливаем состояние загрузки в true перед запросом
        isLoading.value = true
        searchViewModel.getAlbumById(albumId)
        // После получения данных устанавливаем состояние загрузки в false
        isLoading.value = false
    }

    val album = searchViewModel.detailsAlbum

    // Показать индикатор загрузки, если альбом еще не загружен
    if (isLoading.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() // Индикатор загрузки
        }
    } else {
        // Если альбом загружен, показываем данные альбома
        album?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = MainActivity.API_URL + it.poster,
                    contentDescription = "Album Cover",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Безопасно получаем название альбома
                val albumName = it.name ?: "Неизвестное название"
                Text(
                    text = albumName,
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Безопасно получаем имя исполнителя
                val authorName = it.author?.name ?: "Неизвестный исполнитель"
                Text(
                    text = "Исполнитель: $authorName",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Треки:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Получаем список треков, если он существует
                val tracks = it.tracks ?: emptyList()

                LazyColumn {
                    items(tracks) { track ->
                        val trackObj = Track(
                            UUID.fromString(track.trackId),
                            it.poster,
                            track.name,
                            authorName,
                            track.isFavorite
                        )
                        TrackItem(
                            track = trackObj,
                            onClick = {
                                favoriteViewModel.playFavoriteFromTrack(trackObj)
                            },
                            onFavoriteClick = {
                                track.isFavorite = !track.isFavorite
                                favoriteViewModel.setIsFavorite(trackObj, track.isFavorite)
                            }
                        )
                    }
                }
            }
        } ?: run {
            // Если альбом не загружен (album == null), можно показать сообщение об ошибке или другой индикатор
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Ошибка загрузки альбома", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}



