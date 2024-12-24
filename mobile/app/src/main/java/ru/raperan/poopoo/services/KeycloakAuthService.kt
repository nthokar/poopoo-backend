package ru.raperan.poopoo.services

import android.util.Log
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.android.Android
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    var access_token: String,
    var refresh_token: String,
    var expires_in: Int
)

class KeycloakAuthService {

    private val client = HttpClient(Android)
    private var accessToken : String? = null

    suspend fun fetchToken(
        username: String,
        password: String,
        clientId: String,
        clientSecret: String,
        grantType: String = "password"
    ): Result<TokenResponse> {
        return try {
            val response: HttpResponse = client.post("https://keycloak.raperan.ru/realms/poopoo/protocol/openid-connect/token") {
                contentType(ContentType.Application.FormUrlEncoded) // Указываем Content-Type как application/x-www-form-urlencoded
                setBody(
                    FormDataContent(Parameters.build {
                        append("username", username)
                        append("password", password)
                        append("client_id", clientId)
                        append("client_secret", clientSecret)
                        append("grant_type", grantType)
                    })
                )
            }

            if (response.status == HttpStatusCode.OK) {
                val tokenResponse: TokenResponse = Gson().fromJson(response.bodyAsText(), TokenResponse::class.java)// Десериализуем ответ в TokenResponse
                Result.success(tokenResponse) // Возвращаем успешный результат
            } else {
                // Обработка ошибки
                Result.failure(Exception("Ошибка: ${response.status} - ${response.bodyAsText()}"))
            }
        } catch (e: Exception) {
            // Обработка исключений
            Result.failure(e)
        }
    }

    // Метод для обновления токена перед запросами
    suspend fun updateToken() {
        val result = fetchToken(
            username = "dev", // Здесь можно взять эти данные из настроек или пользовательского ввода
            password = "dev",
            clientId = "android-app",
            clientSecret = "Vk6K7UYPylFY1ZNlfB6wXCGfpEdEWd1u"
        )

        result.onSuccess { tokenResponse ->
            accessToken = tokenResponse.access_token
        }.onFailure { error ->
            Log.e("Keycloak", "Ошибка при получении токена: ${error.message}")
            throw Exception("Не удалось получить токен от Keycloak")
        }
    }

    suspend fun getToken(): String {
        // Если токен ещё не получен, обновляем его
        if (accessToken == null) {
            updateToken()
        }
        return accessToken ?: throw Exception("Токен не был получен.")
    }
}

