package com.example.newsapp.domain.articles

import com.example.newsapp.data.models.ArticlesResponseItem
import java.util.*
import kotlin.random.Random

fun ArticlesResponseItem.toUiModel() =
    ArticlesUiModel(
        title ?: "",
        imageUrl ?: "",
        summary ?: "",
        publishedAt ?: "",
        newsSite ?: "",
        id ?: UUID.randomUUID().toString(),
        url ?: ""
    )