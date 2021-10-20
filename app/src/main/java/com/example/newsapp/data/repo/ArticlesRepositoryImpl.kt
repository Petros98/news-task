package com.example.newsapp.data.repo

import com.example.newsapp.data.models.ArticlesResponseItem
import com.example.newsapp.data.services.ArticlesApiService
import com.example.newsapp.data.utils.Result
import com.example.newsapp.data.utils.analyzeResponse
import com.example.newsapp.data.utils.makeApiCall

class ArticlesRepositoryImpl(
    private val apiService: ArticlesApiService
) : ArticlesRepository {

    override suspend fun getArticles(pageNumber: Int, pageSize: Int): Result<List<ArticlesResponseItem>> =
        makeApiCall({
            analyzeResponse(
                apiService.getArticles(
                    pageNumber, pageSize
                )
            )
        })
}