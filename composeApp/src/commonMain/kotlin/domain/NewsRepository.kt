package domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getAll(q: String): Flow<Resource<List<Article>>>

}