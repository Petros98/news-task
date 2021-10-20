package com.example.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.domain.articles.ArticlesUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by sharedViewModel()
    private var binding: FragmentHomeBinding? = null

    private val adapter by lazy {
        NewsAdapter{ item: ArticlesUiModel, addFavorite: Boolean ->
            viewModel.addToFavorites(item,addFavorite)
        }
    }

    private var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            rvNews.adapter = adapter
            root.setOnRefreshListener {
                getData()
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .collectLatest { loadStates: CombinedLoadStates ->
                    when (loadStates.refresh) {
                        is LoadState.Loading -> showLoading(true)
                        is LoadState.Error -> {
                            showLoading(false)
                            showError((loadStates.refresh as LoadState.Error).error.message)
                        }
                        is LoadState.NotLoading -> {
                            showLoading(false)
                            if (loadStates.append.endOfPaginationReached && adapter.itemCount < 1)
                                showError("No Result")
                        }
                        else -> showLoading(false)
                    }
                }
        }

        getData()
    }

    private fun showError(message: String?) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(show: Boolean) {
        binding?.root?.isRefreshing = show
    }

    private fun getData() {
        job?.cancel()
        job = lifecycleScope.launch {
            viewModel.getFeedPagingFlow().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job?.cancel()
        binding = null
    }
}