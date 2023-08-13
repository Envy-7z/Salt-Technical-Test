package com.wisnu.salttechnicaltest.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wisnu.salttechnicaltest.R
import com.wisnu.salttechnicaltest.databinding.ActivityDetailBinding
import com.wisnu.salttechnicaltest.models.Article
import com.wisnu.salttechnicaltest.utils.NewsMapper.toArticleDetail
import com.wisnu.salttechnicaltest.utils.gone
import com.wisnu.salttechnicaltest.utils.setOrGone
import com.wisnu.salttechnicaltest.utils.simpleFormattedDate
import com.wisnu.salttechnicaltest.utils.visible

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    private var article: Article? = null

    companion object {
        const val DATA = "detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        intent?.getBundleExtra(DATA)?.let {
            article = it.toArticleDetail()
        }
        article?.let { setData(it) }
    }

    private fun setData(item: Article) {
        with(binding) {
            swipeRefresh.isEnabled = false
            if (item.urlToImage.isEmpty()) {
                ivBanner.gone()
            } else {
                ivBanner.visible()
                try {
                    Glide.with(binding.root)
                        .load(item.urlToImage)
                        .error(R.drawable.baseline_newspaper_24)
                        .into(ivBanner)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            tvTitle.setOrGone(item.title)
            tvSource.setOrGone(item.authorAndSource())
            tvDate.setOrGone(item.publishedAt.simpleFormattedDate())
            tvContent.setOrGone(item.content)
            tvDescriptions.setOrGone(item.description)
            loadUrl(item.url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadUrl(newsUrl: String) {
        with(binding) {
            webview.settings.javaScriptEnabled = true
            webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    return false
                }
            }
            webview.loadUrl(newsUrl)
        }
    }

}