@file:OptIn(ExperimentalCoroutinesApi::class)

package com.maxi.news_clean_architecture.presentation.news.viewmodel

import app.cash.turbine.test
import com.maxi.news_clean_architecture.base.CoroutineTest
import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.domain.model.Article
import com.maxi.news_clean_architecture.domain.usecase.NewsUseCase
import com.maxi.news_clean_architecture.presentation.base.UiState
import com.maxi.news_clean_architecture.utils.DispatcherProvider
import com.maxi.news_clean_architecture.utils.TestDispatcherProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel

    private val useCase: NewsUseCase = mock()
    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        dispatcherProvider = TestDispatcherProvider(dispatcher)
    }

    @Test
    fun `getNews viewmodel emits Success when useCase returns Success`() = runTest(dispatcher) {
        //arrange
        whenever(useCase()).thenReturn(flow {
            emit(Result.Loading)
            println("Loading")
            emit(Result.Success(listOf()))
            println("Success")
        })

        //act
        viewModel = NewsViewModel(useCase, dispatcherProvider)

        //assert
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(UiState.Success(emptyList<Article>()), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        verify(useCase).invoke()
    }

    @Test
    fun `getNews viewmodel emits Error when useCase returns Error`() = runTest(dispatcher) {
        //arrange
        whenever(useCase()).thenReturn(flow {
            emit(Result.Loading)
            emit(Result.ApiError(400, "Error!"))
        })

        //act
        viewModel = NewsViewModel(useCase, dispatcherProvider)

        //assert
        viewModel.uiState.test {
            assertEquals(UiState.Loading, awaitItem())
            val result = awaitItem()
            assertEquals((result is UiState.ApiError), true)
            assertEquals((result as UiState.ApiError).message, "Error!")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}