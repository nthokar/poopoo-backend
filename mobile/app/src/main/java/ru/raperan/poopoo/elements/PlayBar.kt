package ru.raperan.poopoo.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage

import ru.raperan.poopoo.MainActivity
import ru.raperan.poopoo.R
import ru.raperan.poopoo.data.Track
import ru.raperan.poopoo.viewmodels.FavoriteViewModel
import ru.raperan.poopoo.viewmodels.PlayBarViewModel
import java.util.UUID

//@Composable
//fun PlayBar(
//    model: PlayBarViewModel,
//) {
//    var currentProgress by remember { mutableStateOf(model.currentProgress) }
//    model.start { currentProgress = it }
//    var loading by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope() // Create a coroutine scope
//
//    ConstraintLayout {
//
//        val (screen, box, bar) = createRefs()
//
//        Box(modifier = Modifier
////            .fillMaxSize()
//            .fillMaxWidth()
//            .height(120.dp)
//            .constrainAs(screen)
//        {
//            bottom.linkTo(parent.bottom)
//        }) {
//        }
//
//        Box(modifier = Modifier
////            .fillMaxSize()
//            .background(Color.Gray)
//            .fillMaxWidth()
//            .height(120.dp)
//            .constrainAs(box)
//            {
//                bottom.linkTo(box.bottom)
//            }) {
//        }
//
//        LinearProgressIndicator(
//            progress = { currentProgress },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(12.dp)
//                .constrainAs(bar) {
//                    top.linkTo(box.top)
//                }
//            ,
//            color = Color(0xFF03DAC5)
//        )
//
//    }
//}

@Composable
fun PlayBar(
    model: PlayBarViewModel,
    favoriteViewModel: FavoriteViewModel,
) {
    // Состояние прогресса
    var currentProgress by remember { mutableStateOf(model.currentProgress) }
    var isPlaying by remember { mutableStateOf(model.isPlaying) }
    var isExpanded by remember { mutableStateOf(false) } // Состояние развернутости панели

    // Запуск обновления прогресса
    LaunchedEffect(model) {
        model.start { progress ->
            currentProgress = progress
        }
    }

    if (isExpanded) {
        // Развёрнутое состояние панели
        ExpandedPlayBar(
            playBarViewModel = model,
            favoriteViewModel = favoriteViewModel,
            isPlaying = isPlaying,
            currentProgress = currentProgress,
            onPlayPauseToggle = { play ->
                isPlaying = play
                model.togglePlayPause(play)
            },
            onCollapse = { isExpanded = false },
        )
    } else {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp) // Изменение высоты при разворачивании
                .clickable { isExpanded = true } // Переключение состояния разворачивания
        ) {
            // Определение рефов
            val (screen, box, bar, playPauseButton, details) = createRefs()

            // Фон экрана
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .constrainAs(screen) {
                        bottom.linkTo(parent.bottom)
                    }
            )

            // Серый фон Box
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                    .height(120.dp)
                    .constrainAs(box) {
                        bottom.linkTo(screen.bottom)
                    }
            )

            // Индикатор прогресса
            LinearProgressIndicator(
                progress = currentProgress, // Передаем само значение
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .constrainAs(bar) {
                        top.linkTo(box.top)
                    },
                color = Color(0xFF03DAC5)
            )

            // Кнопка паузы/воспроизведения
            IconButton(
                onClick = {
                    isPlaying = !isPlaying
                    model.togglePlayPause(isPlaying) // Вызов функции модели для управления воспроизведением
                },
                modifier = Modifier
                    .size(48.dp)
                    .constrainAs(playPauseButton) {
                        bottom.linkTo(box.bottom, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White
                )
            }

            // Подробная информация о треке (при развернутом состоянии)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .constrainAs(details) {
                        top.linkTo(bar.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Text(
                text = model.getTrackTitle() ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
                Text(
                text = model.getArtistName() ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
                Text(
                text = model.getAlbumName() ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }

    }

}

@Composable
fun ExpandedPlayBar(
    playBarViewModel: PlayBarViewModel,
    favoriteViewModel: FavoriteViewModel,
    isPlaying: Boolean,
    currentProgress: Float,
    onPlayPauseToggle: (Boolean) -> Unit,
    onCollapse: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val track = playBarViewModel.playService.currentTrack
            val trackObj = Track(
                UUID.fromString(track?.trackId),
                track?.poster ?: "",
                track?.name ?: "",
                track?.authorName ?: "",
                track?.isFavorite ?: false
            )
            // Кнопка сворачивания
            IconButton(
                onClick = onCollapse,
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Collapse",
                    tint = Color.Black
                )
            }

            // Постер альбома
//            AsyncImage(
//                painter = painterResource(id = R.drawable.album_poster),
//                contentDescription = "Album Cover",
//                modifier = Modifier
//                    .size(200.dp)
//                    .clip(RoundedCornerShape(8.dp))
//            )

            AsyncImage(
                model = MainActivity.API_URL + track?.poster,
                modifier = Modifier
                    .size(120.dp)
                ,
                contentDescription = "Изображение",
                error = painterResource(id = R.drawable.album_poster), // Подставляется дефолтная картинка при ошибке
                placeholder = painterResource(id = R.drawable.album_poster) // Заглушка при загрузке

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = playBarViewModel.playService.currentTrack?.name ?: "",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = playBarViewModel.playService.currentTrack?.authorName ?: "",
                style = MaterialTheme.typography.titleLarge
            )

            // Таймлайн
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Slider(
                    value = currentProgress,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "0:00", color = Color.Black)
                    Text(text = "3:45", color = Color.Black) // Пример длительности трека
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопки управления
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                }) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous Track",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = {
                    onPlayPauseToggle(!isPlaying)
                }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = {
                    playBarViewModel.playNextTrack()
                }) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next Track",
                        tint = Color.Black
                    )
                }
//                IconButton(
//                    onClick = {
//                        trackObj.isFavorite = !trackObj.isFavorite
//                        favoriteViewModel.setIsFavorite(trackObj, trackObj.isFavorite)
//                              },
//                    modifier = Modifier.align(Alignment.CenterVertically)
//                ) {
//                    Icon(
//                        imageVector = if (track?.isFavorite ?: false) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
//                        contentDescription = if (track?.isFavorite ?: false) "Удалить из избранного" else "Добавить в избранное",
//                        tint = if (track?.isFavorite ?: false) MaterialTheme.colorScheme.primary else Color.Gray
//                    )
//                }
            }
        }
    }
}