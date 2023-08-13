package com.wisnu.salttechnicaltest.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)

data class Article(
    var author: String,
    var content: String,
    var description: String,
    var publishedAt: String,
    var source: Source,
    var title: String,
    var url: String,
    var urlToImage: String
) {
    fun authorAndSource(): String = when {
        this.author.isNotEmpty() && this.source.name.isNotEmpty() -> "${this.author} (${this.source.name})"
        this.author.isEmpty() -> this.source.name
        this.source.name.isEmpty() -> this.author
        else -> ""
    }
}

data class Source(
    var id: String,
    var name: String
)