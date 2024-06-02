package data

import data.model.ArticlesResponse
import data.model.toUI
import domain.Article
import domain.NewsRepository
import domain.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.parameters
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class NewsRepositoryImpl(
    private val newsClient: HttpClient
) : NewsRepository {
    override suspend fun getAll(q: String): Flow<Resource<List<Article>>> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = newsClient.get("everything") {
                    parameter("q", q)
                }
                if (response.status == HttpStatusCode.OK) {
                    val news = response.call.body<ArticlesResponse>().articles.map { it.toUI() }
                    emit(Resource.Success(news))
                } else {
                    throw IOException("server not responding")
                }
            } catch (e: Exception) {
                emit(Resource.Failure(e.message ?: "cannot receive data"))
            }
        }
    }
}