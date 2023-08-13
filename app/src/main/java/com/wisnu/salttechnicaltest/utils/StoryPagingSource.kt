package com.wisnu.salttechnicaltest.utils

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wisnu.salttechnicaltest.api.ApiService
import com.wisnu.salttechnicaltest.models.Article

class StoryPagingSource(
    private val apiService: ApiService,
) : PagingSource<Int, Article>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val hashMap = HashMap<String, Int>()
            hashMap["page"] = page
            hashMap["size"] = params.loadSize
            val responseData = apiService.getTopNews(page).articles
            Log.d("andrian", "$responseData")

            LoadResult.Page(
                data = responseData ?: emptyList(),
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}