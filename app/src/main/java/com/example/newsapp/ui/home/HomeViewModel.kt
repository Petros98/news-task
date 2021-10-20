package com.example.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.newsapp.domain.articles.ArticlesInteractor
import com.example.newsapp.domain.articles.ArticlesUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val interactor: ArticlesInteractor
) : ViewModel() {

    private var currentPage: Int = 1

    private var currentResult: Flow<PagingData<ArticlesUiModel>>? = null

    private val _favorites by lazy { MutableStateFlow<List<ArticlesUiModel>>(emptyList()) }
    val favorites: StateFlow<List<ArticlesUiModel>> get() = _favorites

    fun getFeedPagingFlow(): Flow<PagingData<ArticlesUiModel>> {
        val lastResult = currentResult
        if (currentPage == 1 && lastResult != null) {
            return lastResult
        }
        return interactor.getArticles(_favorites.value.map { it.id } as ArrayList<String>).apply {
            currentResult = this
        }
    }

    fun addToFavorites(item: ArticlesUiModel, addFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _favorites.value =
                interactor.addToFavorites(_favorites.value,item,addFavorite)
        }
    }
}