package data.model

import domain.Article
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

fun ArticleDto.toUI(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        urlToImage = urlToImage
    )
}