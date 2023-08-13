package com.wisnu.salttechnicaltest

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.wisnu.salttechnicaltest.databinding.ActivityMainBinding
import com.wisnu.salttechnicaltest.models.Article
import com.wisnu.salttechnicaltest.ui.NewsAdapter
import com.wisnu.salttechnicaltest.ui.activity.DetailActivity
import com.wisnu.salttechnicaltest.ui.viewmodel.NewsViewModel
import com.wisnu.salttechnicaltest.utils.LoadingStateAdapter
import com.wisnu.salttechnicaltest.utils.NewsMapper.toBundle
import com.wisnu.salttechnicaltest.utils.visible

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val allNewsAdapter: NewsAdapter by lazy {
        NewsAdapter(
            callback = ::openDetail
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
        setObservable()

    }

    private fun setObservable() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val newsViewModel: NewsViewModel by viewModels {
            factory
        }
        newsViewModel.getTopNews().observe(this@MainActivity) {
            binding.rvAllNews.visible()
            allNewsAdapter.submitData(lifecycle, it)
        }

    }

    private fun setupViews() {
        with(binding) {
            swipeRefresh.setOnRefreshListener { allNewsAdapter.refresh() }

            rvAllNews.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL, false
            )
            rvAllNews.adapter = allNewsAdapter.withLoadStateFooter(LoadingStateAdapter {
                allNewsAdapter.retry()
            })

            allNewsAdapter.addLoadStateListener {
                binding.swipeRefresh.isRefreshing = it.refresh is LoadState.Loading

                val isErrorOrEmpty = it.refresh is LoadState.Error ||
                        (it.refresh is LoadState.NotLoading && allNewsAdapter.itemCount == 0)
                tvLoadState.isVisible = isErrorOrEmpty
                if (it.refresh is LoadState.Error) {
                    tvLoadState.text = getString(R.string.label_error_data)
                }

                if (it.refresh is LoadState.NotLoading && allNewsAdapter.itemCount == 0) {
                    tvLoadState.text = getString(R.string.label_empty_data)
                }
                rvAllNews.isVisible = !isErrorOrEmpty
            }
        }
    }


    private fun openDetail(item: Article) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.DATA, item.toBundle())
        })
    }
}