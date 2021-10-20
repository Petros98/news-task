package com.example.newsapp.domain.articles

import androidx.paging.*
import com.example.newsapp.data.NETWORK_PAGE_SIZE
import com.example.newsapp.data.NETWORK_START_PAGE_INDEX
import com.example.newsapp.data.models.ArticlesResponseItem
import com.example.newsapp.data.repo.ArticlesRepository
import com.example.newsapp.data.utils.Result.*
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException

class ArticlesUseCase(
    private val repository: ArticlesRepository
) : ArticlesInteractor{

    private var favoritesIds: ArrayList<String>? = null

    override fun getArticles(favoritesIds: ArrayList<String>): Flow<PagingData<ArticlesUiModel>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { DataPagingSource() }
        ).flow
    }

    private inner class DataPagingSource : PagingSource<Int, ArticlesUiModel>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesUiModel> {
            val position = params.key ?: 1
            return try {
                val data: List<ArticlesUiModel> = getList(position)

                val nextKey = if (data.isNullOrEmpty()) {
                    null
                } else {
                    position + 1
                }
                LoadResult.Page(
                    data = data,
                    prevKey = if (position == NETWORK_START_PAGE_INDEX) null else position - 1,
                    nextKey = nextKey
                )
            } catch (exception: IOException) {
                LoadResult.Error(exception)
            } catch (exception: HttpException) {
                LoadResult.Error(exception)
            }
        }

        override fun getRefreshKey(state: PagingState<Int, ArticlesUiModel>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }
    }

    private suspend fun getList(position: Int): List<ArticlesUiModel> {
        val response: List<ArticlesResponseItem?>? = when (val result = repository.getArticles(position, NETWORK_PAGE_SIZE)) {
            is Error -> emptyList<ArticlesResponseItem>()
            is Success -> result.data
        }

        return response?.run {
            if (!isNullOrEmpty()) {
                this.mapNotNull { it?.toUiModel() }
            } else {
                emptyList()
            }
        } ?: emptyList()
    }

    override suspend fun addToFavorites(
        current: List<ArticlesUiModel>,
        item: ArticlesUiModel,
        addFavorite: Boolean
    ): List<ArticlesUiModel> {
        return mutableListOf<ArticlesUiModel>().apply {
            addAll(current)
            item.isFavorite = addFavorite
            val index = current.indexOf(find { it.id == item.id })
            if (index != -1){
                if (addFavorite) set(index,item) else removeAt(index)
            }else add(item)
        }
    }
}