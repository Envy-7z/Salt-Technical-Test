package com.wisnu.salttechnicaltest.api

import com.wisnu.salttechnicaltest.models.NewsResponse
import retrofit2.http.*

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopNews(
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = 10,
        @Query("country") country: String? = "id",
        @Query("sources") source: String? = null,
        @Query("category") category: String? = null,
        @Query("apiKey") apikey : String? = "2ed1fd4648494040b64ff6585d0f0392"
    ): NewsResponse

}