package com.example.newsapp.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemNewsBinding
import com.example.newsapp.domain.articles.ArticlesUiModel

class FavoritesAdapter: ListAdapter<ArticlesUiModel,FavoritesAdapter.ArticleViewHolder>(DIFF_UTIL){

    inner class ArticleViewHolder(
        private val binding: ItemNewsBinding
    ): RecyclerView.ViewHolder(binding.root){


        fun bind(article: ArticlesUiModel){
            with(binding){
                binding.checkboxFavorite.isVisible = false
                imageView.load(article.imageUrl){
                    placeholder(R.color.light_gray)
                    error(android.R.color.holo_red_light)
                    crossfade(true)
                    crossfade(500)
                }
                title.text = article.title
                category.text = article.newsSite
                date.text = article.publishedAt
            }
        }

    }

    companion object{
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ArticlesUiModel>(){
            override fun areItemsTheSame(
                oldItem: ArticlesUiModel,
                newItem: ArticlesUiModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ArticlesUiModel,
                newItem: ArticlesUiModel
            ): Boolean = oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
}