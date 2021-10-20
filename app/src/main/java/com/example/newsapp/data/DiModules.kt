package com.example.newsapp.data

import com.example.newsapp.data.repo.ArticlesRepository
import com.example.newsapp.data.repo.ArticlesRepositoryImpl
import com.example.newsapp.data.services.ArticlesApiService
import com.example.newsapp.domain.articles.ArticlesInteractor
import com.example.newsapp.domain.articles.ArticlesUseCase
import com.example.newsapp.ui.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
    }

}

val articlesModule = module {

    single { get<Retrofit>().create(ArticlesApiService::class.java) }

    single<ArticlesRepository> { ArticlesRepositoryImpl(get()) }

    single<ArticlesInteractor> { ArticlesUseCase(get()) }

    viewModel { HomeViewModel(get()) }
}
