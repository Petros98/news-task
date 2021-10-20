package com.example.newsapp.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.databinding.FragmentFavoritesBinding
import com.example.newsapp.ui.home.HomeViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FavoritesFragment : Fragment() {

    private val viewModel: HomeViewModel by sharedViewModel()

    private var binding: FragmentFavoritesBinding? = null

    private val adapter by lazy {
        FavoritesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return FragmentFavoritesBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rvFavorites?.adapter = adapter

        viewModel.favorites.onEach {
            showList(it.isNotEmpty())
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }

    private fun showList(show: Boolean) {
        binding?.rvFavorites?.isVisible = show
        binding?.emptyTv?.isVisible = !show
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}