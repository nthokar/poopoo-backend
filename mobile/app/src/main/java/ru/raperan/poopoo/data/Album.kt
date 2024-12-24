package ru.raperan.poopoo.data

import java.util.UUID

data class Album(
    val albumId: UUID,
    val albumName: String,
    val poster: String
)
