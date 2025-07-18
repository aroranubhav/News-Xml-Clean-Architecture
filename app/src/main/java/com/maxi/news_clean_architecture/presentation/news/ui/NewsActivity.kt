package com.maxi.news_clean_architecture.presentation.news.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.maxi.news_clean_architecture.databinding.ActivityNewsBinding
import com.maxi.news_clean_architecture.presentation.base.UiState
import com.maxi.news_clean_architecture.presentation.news.adapter.NewsAdapter
import com.maxi.news_clean_architecture.presentation.news.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel

    private lateinit var binding: ActivityNewsBinding

    @Inject
    lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() {
        viewModel = ViewModelProvider(this)[NewsViewModel::class]
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = this@NewsActivity.adapter
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@NewsActivity,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        observeDataAndUpdateUi()
    }

    private fun observeDataAndUpdateUi() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            adapter.setData(state.data)
                            updateProgressBarVisibility(false)
                        }

                        is UiState.ApiError -> {
                            val error = "${state.code}: ${state.message}"
                            showError(error)
                            updateProgressBarVisibility(false)
                        }

                        is UiState.DatabaseError -> {
                            showError(state.error)
                            updateProgressBarVisibility(false)
                        }

                        is UiState.NetworkError -> {
                            showError()
                            updateProgressBarVisibility(false)
                        }

                        is UiState.UnknownError -> {
                            showError()
                            updateProgressBarVisibility(false)
                        }

                        is UiState.Loading -> {
                            updateProgressBarVisibility(true)
                        }
                    }
                }
            }
        }
    }

    private fun showError(error: String = "") {
        val errorMessage = "Encountered error while fetching news $error"
        Snackbar.make(
            binding.root,
            errorMessage,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun updateProgressBarVisibility(isVisible: Boolean) {
        binding.pbNews.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}