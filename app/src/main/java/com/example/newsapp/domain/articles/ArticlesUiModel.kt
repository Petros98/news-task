package com.example.newsapp.domain.articles

data class ArticlesUiModel(
    val title: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val newsSite: String,
    val id: String,
    val url: String,
){
    var isFavorite: Boolean = false
}
