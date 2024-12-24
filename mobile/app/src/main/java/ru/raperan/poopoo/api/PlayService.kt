package ru.raperan.poopoo.api

import android.util.Log
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import java.util.UUID


import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import ru.raperan.poopoo.services.KeycloakAuthService
import com.google.gson.reflect.TypeToken;
import io.ktor.client.request.forms.submitForm
import ru.raperan.poopoo.MainActivity
import ru.raperan.poopoo.data.Track

class PlayService(
    private val apiUrl: String = MainActivity.API_URL,
    private val keycloakAuthService: KeycloakAuthService // Добавляем KeycloakService для получения токена
) {
    val client = HttpClient(Android) { }
    var currentTrack : TrackBaseInfoDto? = null
    private var accessToken: String? = null

    suspend fun play(collectionId: UUID): TrackBaseInfoDto? {
        val client = HttpClient(Android)
        try {
            val token = keycloakAuthService.getToken() // Получаем токен

            val response = client.post(apiUrl + "/collections/" + collectionId.toString() + "/play") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token") // Добавляем токен в заголовок
                }
            }
            val parsedResponseBody = Json.decodeFromString<TrackBaseInfoDto>(response.bodyAsText())
            return parsedResponseBody
        } catch (e: Exception) {
            e.message?.let { Log.e("=====[TestAPI]=====", it) }
        } finally {
            client.close()
        }
        throw RuntimeException("Не удалось выполнить запрос")
    }

    suspend fun findAlbum(albumId: String): AlbumBaseInfoDto? {
        val client = HttpClient(Android)
        try {
            val token = keycloakAuthService.getToken() // Получаем токен

            val response = client.get(apiUrl + "/findAlbumById/" + albumId) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token") // Добавляем токен в заголовок
                }
            }
            val parsedResponseBody = Gson().fromJson<AlbumBaseInfoDto>(response.bodyAsText(), AlbumBaseInfoDto::class.java)
            return parsedResponseBody
        } catch (e: Exception) {
            e.message?.let { Log.e("=====[TestAPI]=====", it) }
        } finally {
            client.close()
        }
        throw RuntimeException("Не удалось выполнить запрос")
    }

    suspend fun getFavoriteTracks(): List<TrackBaseInfoDto>? {
        val client = HttpClient(Android)
        try {
            val token = keycloakAuthService.getToken() // Получаем токен

            val response = client.get(apiUrl + "/collections/favoriteTracks") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token") // Добавляем токен в заголовок
                }
            }
            val itemType = object : TypeToken<List<TrackBaseInfoDto>>() {}.type
            val parsedResponseBody = Gson().fromJson<List<TrackBaseInfoDto>>(response.bodyAsText(), itemType)
//            parsedResponseBody.forEach({
//                it.poster = MainActivity.API_URL + it.poster
//            })
            return parsedResponseBody
        } catch (e: Exception) {
            e.message?.let { Log.e("=====[TestAPI]=====", it) }
        } finally {
            client.close()
        }
        throw RuntimeException("Не удалось выполнить запрос")
    }

    suspend fun playFavoriteFromTrack(track: Track): TrackBaseInfoDto? {
        val client = HttpClient(Android)
        try {
            val token = keycloakAuthService.getToken() // Получаем токен

            val response = client.post(apiUrl + "/collections/favoriteTracks/ " + track.trackId) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token") // Добавляем токен в заголовок
                }
            }
            val parsedResponseBody =  Gson().fromJson<TrackBaseInfoDto>(response.bodyAsText(), TrackBaseInfoDto::class.java)
            this.currentTrack = parsedResponseBody
            return parsedResponseBody
        } catch (e: Exception) {
            e.message?.let { Log.e("=====[TestAPI]=====", it) }
        } finally {
            client.close()
        }
        throw RuntimeException("Не удалось выполнить запрос")
    }

    suspend fun playNextTrack(): TrackBaseInfoDto? {
        val client = HttpClient(Android)
        try {
            val token = keycloakAuthService.getToken() // Получаем токен

            val response = client.post(apiUrl + "/collections/playNext") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token") // Добавляем токен в заголовок
                }
            }
            val parsedResponseBody =  Gson().fromJson<TrackBaseInfoDto>(response.bodyAsText(), TrackBaseInfoDto::class.java)
            this.currentTrack = parsedResponseBody
            return parsedResponseBody
        } catch (e: Exception) {
            e.message?.let { Log.e("=====[TestAPI]=====", it) }
        } finally {
            client.close()
        }
        throw RuntimeException("Не удалось выполнить запрос")
    }

    suspend fun fetchAutocomplete(query: String, filter: String): List<Any> {
        val client = HttpClient(Android)
        try {
            val token = keycloakAuthService.getToken() // Получаем токен

            val response = client.submitForm(
                    url = apiUrl + "/search/" + query,
            formParameters = Parameters.build {
                append("filter", filter)  // Добавляем фильтр в параметры формы
            }
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token") // Добавляем токен в заголовок
                }
            }

            // В зависимости от фильтра парсим разные типы
            return when (filter) {
                "tracks" -> {
                    val itemType = object : TypeToken<List<TrackBaseInfoDto>>() {}.type
                    Gson().fromJson<List<TrackBaseInfoDto>>(response.bodyAsText(), itemType)
                }
                "albums" -> {
                    val itemType = object : TypeToken<List<AlbumBaseInfoDto>>() {}.type
                    Gson().fromJson<List<AlbumBaseInfoDto>>(response.bodyAsText(), itemType)
                }
                "artists" -> {
                    val itemType = object : TypeToken<List<ArtistBaseInfoDto>>() {}.type
                    Gson().fromJson<List<ArtistBaseInfoDto>>(response.bodyAsText(), itemType)
                }
                else -> {
                    throw IllegalArgumentException("Unknown filter: $filter")
                }
            }

        } catch (e: Exception) {
            e.message?.let { Log.e("=====[TestAPI]=====", it) }
        } finally {
            client.close()
        }

        throw RuntimeException("Не удалось выполнить запрос")
    }

    suspend fun setIsFavorite(trackId: String, isFavorite: Boolean) {
        val client = HttpClient(Android)
        try {
            val token = keycloakAuthService.getToken() // Получаем токен

            val response = client.submitForm(
                url = apiUrl + "/setIsFavorite/" + trackId,
                formParameters = Parameters.build {
                    append("isFavorite", isFavorite.toString())  // Добавляем фильтр в параметры формы
                }
            ) {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token") // Добавляем токен в заголовок
                }
            }

            // В зависимости от фильтра парсим разные типы
        } catch (e: Exception) {
            e.message?.let { Log.e("=====[TestAPI]=====", it) }
        } finally {
            client.close()
        }
    }

}

data class TrackBaseInfoDto(
    val trackId: String,
    val name: String,
    val fileUrl: String,
    var poster: String,
    val authorName: String,
    var isFavorite: Boolean,

    )

data class AlbumBaseInfoDto(
    val id: String,
    val name: String,
    val poster: String,
    val author: ArtistBaseInfoDto,
    val tracks: List<AlbumTracksItemBaseInfoDto>
)

data class AlbumTracksItemBaseInfoDto(
    val trackNumber: Int,
    val name: String,
    val trackId: String,
    val fileUrl: String,
    var isFavorite: Boolean
)

data class ArtistBaseInfoDto(
    val id: String,
    val name: String,
)



