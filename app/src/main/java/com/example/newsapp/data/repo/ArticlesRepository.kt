package com.example.newsapp.data.repo

import com.example.newsapp.data.models.ArticlesResponseItem
import com.example.newsapp.data.utils.Result

interface ArticlesRepository {

    suspend fun getArticles(pageNumber: Int, pageSize: Int): Result<List<ArticlesResponseItem>>
}