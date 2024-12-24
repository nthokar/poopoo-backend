package ru.raperan.poopoo.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.UUID

data class Track(
    val trackId: UUID,
    val poster: String,
    val trackName: String,
    val authorName: String,
    val initialIsFavorite: Boolean
) {
    var isFavorite by mutableStateOf(initialIsFavorite)
}
