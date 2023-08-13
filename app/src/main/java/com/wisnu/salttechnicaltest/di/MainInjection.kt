package com.wisnu.salttechnicaltest.di

import android.content.Context
import com.wisnu.salttechnicaltest.api.ApiConfig
import com.wisnu.salttechnicaltest.repositories.NewsRepository


object MainInjection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiClient()
        return NewsRepository(apiService)
    }
}