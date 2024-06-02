package domain

import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getAll(): Flow<Resource<List<Article>>>

}