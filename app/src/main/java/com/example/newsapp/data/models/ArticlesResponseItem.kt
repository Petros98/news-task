package com.example.newsapp.data.models

import com.squareup.moshi.Json

data class ArticlesResponseItem(

    @field:Json(name="summary")
    val summary: String? = null,

    @field:Json(name="featured")
    val featured: Boolean? = null,

    @field:Json(name="publishedAt")
    val publishedAt: String? = null,

    @field:Json(name="imageUrl")
    val imageUrl: String? = null,

    @field:Json(name="newsSite")
    val newsSite: String? = null,

    @field:Json(name="id")
    val id: String? = null,

    @field:Json(name="title")
    val title: String? = null,

    @field:Json(name="url")
    val url: String? = null,

    @field:Json(name="launches")
    val launches: List<Any?>? = null,

    @field:Json(name="events")
    val events: List<Any?>? = null,

    @field:Json(name="updatedAt")
    val updatedAt: String? = null
)
