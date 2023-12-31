package com.wisnu.salttechnicaltest

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wisnu.salttechnicaltest.di.MainInjection
import com.wisnu.salttechnicaltest.repositories.NewsRepository
import com.wisnu.salttechnicaltest.ui.viewmodel.NewsViewModel

class ViewModelFactory(private val repository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> NewsViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.simpleName)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(MainInjection.provideRepository(context))
            }.also { instance = it }
        }
    }
}