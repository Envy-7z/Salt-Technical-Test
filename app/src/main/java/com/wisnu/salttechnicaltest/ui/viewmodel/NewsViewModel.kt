package com.wisnu.salttechnicaltest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wisnu.salttechnicaltest.models.Article
import com.wisnu.salttechnicaltest.repositories.NewsRepository

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    fun getTopNews(): LiveData<PagingData<Article>> {
        return  repository.getTopNews().cachedIn(viewModelScope)
    }
}