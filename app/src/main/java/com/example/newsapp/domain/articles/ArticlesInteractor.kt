package com.example.newsapp.domain.articles

import androidx.paging.PagingData

interface ArticlesInteractor {

    fun getArticles(favoritesIds: ArrayList<String>): kotlinx.coroutines.flow.Flow<PagingData<ArticlesUiModel>>

    suspend fun addToFavorites(
        current: List<ArticlesUiModel>,
        item: ArticlesUiModel,
        addFavorite: Boolean
    ): List<ArticlesUiModel>
}