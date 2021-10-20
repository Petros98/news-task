package com.example.newsapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemNewsBinding
import com.example.newsapp.domain.articles.ArticlesUiModel

class NewsAdapter(
    private val onFavoriteClick: (item: ArticlesUiModel, addFavorites: Boolean) -> Unit
): PagingDataAdapter<ArticlesUiModel,NewsAdapter.ArticleViewHolder>(DIFF_UTIL){

    inner class ArticleViewHolder(
        private val binding: ItemNewsBinding
    ): RecyclerView.ViewHolder(binding.root){

        private val item: ArticlesUiModel?
            get() = getItem(bindingAdapterPosition)

        fun bind(article: ArticlesUiModel){
            with(binding){
                imageView.load(article.imageUrl){
                    placeholder(R.color.light_gray)
                    error(android.R.color.holo_red_light)
                    crossfade(true)
                    crossfade(500)
                }
                checkboxFavorite.isChecked = item?.isFavorite ?: false
                title.text = article.title
                category.text = article.newsSite
                date.text = article.publishedAt
            }
        }

        init {
            binding.checkboxFavorite.run {
                setOnCheckedChangeListener { _, checked ->
                    item.takeIf { it?.isFavorite != checked }?.let {
                        isChecked = false
                        onFavoriteClick.invoke(it, checked)
                        item?.id?.let { it1 -> addToFavorites(it1,checked) }
                    }
                }
            }
        }
    }

    fun addToFavorites(id: String, add: Boolean) {
        val items: List<ArticlesUiModel> = snapshot().items
        val indexOfFirst = items.indexOfFirst { it.id == id }
        items.getOrNull(indexOfFirst)?.apply {
            isFavorite = add
            notifyItemChanged(indexOfFirst)
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