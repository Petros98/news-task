package com.example.newsapp.data.models

import com.squareup.moshi.Json

data class ArticlesResponse(

	@field:Json(name="ArticlesResponse")
	val articlesResponse: List<ArticlesResponseItem?>? = null
)

