package ru.raperan.poopoo.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.raperan.poopoo.MainActivity
import ru.raperan.poopoo.data.Track

@Composable
fun TrackItem(
    track: Track,
    onFavoriteClick: (Track) -> Unit,
    onClick: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
        ) {
            // Постер трека
            AsyncImage(
                model = MainActivity.API_URL + track.poster,
                modifier = Modifier
                    .size(120.dp),
                contentDescription = "Изображение"
            )
            Spacer(modifier = Modifier.width(4.dp))

            // Информация о треке
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = track.trackName,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${track.authorName} год",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Иконка сердечка
            IconButton(
                onClick = { onFavoriteClick(track) },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = if (track.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (track.isFavorite) "Удалить из избранного" else "Добавить в избранное",
                    tint = if (track.isFavorite) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }

        // Разделитель
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Color.DarkGray)
        )
    }
}