package com.wisnu.salttechnicaltest.repositories

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.wisnu.salttechnicaltest.api.ApiService
import com.wisnu.salttechnicaltest.models.*
import com.wisnu.salttechnicaltest.utils.StoryPagingSource

class NewsRepository(private val apiService: ApiService) {

    fun getTopNews(): LiveData<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }
}