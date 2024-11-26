package ru.raperan.poopoo.api

import android.util.Log
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import java.util.UUID


//define a suspend fun
class KtorGateway(
    val apiUrl: String = "https://api.kinopoisk.dev/v1.4"
) {

    suspend fun play(collectionId: UUID): MusicBaseInfoDto? {
        val client = HttpClient(Android)
        try {
            val response = client.post(apiUrl + "/collections/" + collectionId.toString() + "/play")
            val parsedResponseBody = Gson().fromJson(response.bodyAsText(), MusicBaseInfoDto::class.java)
            return parsedResponseBody
        } catch (e: Exception) {
            // handle exception
            e.message?.let { Log.e("=====[TestAPI]=====", it) }


        } finally {
            client.close()
        }
        throw RuntimeException()

    }

    suspend fun playNext(collectionId: UUID): MusicBaseInfoDto? {
        val client = HttpClient(Android)
        try {
            val response = client.post(apiUrl + "/collections/" + collectionId.toString() + "/playNext")
            val parsedResponseBody = Gson().fromJson(response.bodyAsText(), MusicBaseInfoDto::class.java)
            return parsedResponseBody
        } catch (e: Exception) {
            // handle exception
            e.message?.let { Log.e("=====[TestAPI]=====", it) }


        } finally {
            client.close()
        }
        throw RuntimeException()

    }
}

class MusicBaseInfoDto(
    val name: String,
    val fileUrl: String
) {

}
