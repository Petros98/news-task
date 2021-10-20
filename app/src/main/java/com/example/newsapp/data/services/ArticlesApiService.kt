package com.example.newsapp.data.services

import com.example.newsapp.data.models.ArticlesResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesApiService {

    @GET("/api/v2/articles")
    suspend fun getArticles(
        @Query("_start") pageNumber: Int,
        @Query("_limit") pageSize: Int
    ): Response<List<ArticlesResponseItem>>
}