package ch.dreipol.multiplatform.reduxsample.shared.network

import ch.dreipol.dreimultiplatform.kermit
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

object ServiceFactory {

    fun client() = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            logger = HttpLogger()
            level = LogLevel.ALL
        }
        defaultRequest {
            url.takeFrom(
                URLBuilder().takeFrom(Url("https://data.stadt-zuerich.ch/api/3/action")).apply {
                    encodedPath += url.encodedPath
                }
            )
            parameter("limit", Long.MAX_VALUE)
        }
    }
}

class HttpLogger : Logger {
    override fun log(message: String) {
        kermit().d { message }
    }
}