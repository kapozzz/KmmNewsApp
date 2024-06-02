package data.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponse(
    val articles: List<ArticleDto>,
    val status: String?,
    val totalResults: Int?
)