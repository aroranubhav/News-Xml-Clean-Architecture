package com.maxi.news_clean_architecture.domain.usecase

import app.cash.turbine.test
import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.domain.model.Article
import com.maxi.news_clean_architecture.domain.repository.NewsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class NewsUseCaseTest {

    private lateinit var useCase: NewsUseCase

    private val repository: NewsRepository = mock()
    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        useCase = DefaultNewsUseCase(repository)
    }

    @Test
    fun `getNews emits Loading and then Success`() = runTest(dispatcher) {
        //arrange
        val articles = listOf(
            Article("A", "B", "C", "D", "E")
        )
        whenever(repository.getNews()).thenReturn(
            flowOf(
                Result.Loading,
                Result.Success(articles)
            )
        )

        //act
        val flow = useCase()

        //assert
        flow.test {
            assertEquals(Result.Loading, awaitItem())
            val result = awaitItem()
            assertEquals(articles, (result as Result.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getNews emits Loading and then API Error`() = runTest(dispatcher) {
        //arrange
        whenever(repository.getNews()).thenReturn(
            flowOf(
                Result.Loading,
                Result.ApiError(404, "Not found!")
            )
        )
        //act
        val flow = useCase()

        //assert
        flow.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(404, (awaitItem() as Result.ApiError).code)
            cancelAndIgnoreRemainingEvents()
        }
    }
}