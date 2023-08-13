package com.wisnu.salttechnicaltest.api

import com.wisnu.salttechnicaltest.BuildConfig.BASE_URL
import com.wisnu.salttechnicaltest.BuildConfig.DEBUG
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {

    companion object {
        fun getApiClient(): ApiService {
            val loggingInterceptor = if (DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .pingInterval(40, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor {chain ->
                    val url = chain.request().url.newBuilder()
                        .addQueryParameter("apiKey", apiToken).build()
                    val request = chain.request().newBuilder().url(url).build()
                    return@addInterceptor chain.proceed(request)
                }
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }

        private const val apiToken = "5e6647c81dc6436596c3b2101690720e"
    }
}