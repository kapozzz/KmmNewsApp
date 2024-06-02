package data.ktor

import data.NewsRepositoryImpl
import domain.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

const val API_KEY = "2826c733a81a43f7899d183bf08d640d"
const val BASE_URL = "newsapi.org/v2"

val networkModule = module {

    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header("X-Api-Key", API_KEY)
                url {
                    host = BASE_URL
                }
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        // TODO не очень понятно почему с println() это работает
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }

    factory<NewsRepository> { NewsRepositoryImpl(get()) }

}