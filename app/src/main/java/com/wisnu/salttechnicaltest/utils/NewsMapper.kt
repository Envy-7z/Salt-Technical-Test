package com.wisnu.salttechnicaltest.utils

import android.os.Bundle
import com.wisnu.salttechnicaltest.models.Article
import com.wisnu.salttechnicaltest.models.ArticleResponse
import com.wisnu.salttechnicaltest.models.Source
import com.wisnu.salttechnicaltest.models.SourceResponse

object NewsMapper {

    fun ArticleResponse.toModel() = Article(
        title = title.orEmpty(),
        author = author.orEmpty(),
        source = source?.toModel() ?: emptySource(),
        description = description.orEmpty(),
        url =  url.orEmpty(),
        urlToImage = urlToImage.orEmpty(),
        publishedAt = publishedAt.orEmpty(),
        content = content.orEmpty(),
    )

    fun SourceResponse.toModel() = Source(
        id = id.orEmpty(),
        name =name.orEmpty(),
    )

    private fun emptySource() = Source("","")


    fun Bundle.toArticleDetail(): Article {
        return Article(
            author = this.getString("author").orEmpty(),
            title = this.getString("title").orEmpty(),
            source = emptySource().apply {
                id = this@toArticleDetail.getString("source").orEmpty()
                name = this@toArticleDetail.getString("source").orEmpty()
            },
            description = this.getString("description").orEmpty(),
            url =  this.getString("url").orEmpty(),
            urlToImage = this.getString("urlToImage").orEmpty(),
            publishedAt = this.getString("publishedAt").orEmpty(),
            content = this.getString("content").orEmpty(),
        )
    }

    fun Article.toBundle(): Bundle {
        return Bundle().apply {
            putString("author", this@toBundle.author)
            putString("title", this@toBundle.title)
            putString("source", this@toBundle.source.name)
            putString("description", this@toBundle.description)
            putString("url", this@toBundle.url)
            putString("urlToImage", this@toBundle.urlToImage)
            putString("publishedAt", this@toBundle.publishedAt)
            putString("content", this@toBundle.content)
        }
    }

}