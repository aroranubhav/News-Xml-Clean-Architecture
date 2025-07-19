package com.maxi.news_clean_architecture.data.repository

import app.cash.turbine.test
import com.maxi.news_clean_architecture.data.common.Result
import com.maxi.news_clean_architecture.data.local.dao.NewsDao
import com.maxi.news_clean_architecture.data.local.model.ArticleEntity
import com.maxi.news_clean_architecture.data.local.model.toDomain
import com.maxi.news_clean_architecture.data.remote.api.NetworkService
import com.maxi.news_clean_architecture.data.remote.model.ArticleDto
import com.maxi.news_clean_architecture.data.remote.model.NewsResponse
import com.maxi.news_clean_architecture.data.remote.model.toDomain
import com.maxi.news_clean_architecture.data.remote.model.toEntity
import com.maxi.news_clean_architecture.domain.repository.NewsRepository
import com.maxi.news_clean_architecture.utils.TestDispatcherProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DefaultNewsRepositoryTest {

    private lateinit var repository: NewsRepository

    private val networkService: NetworkService = mock()
    private val newsDao: NewsDao = mock()
    private val dispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        val dispatcherProvider = TestDispatcherProvider(dispatcher)
        repository = DefaultNewsRepository(networkService, newsDao, dispatcherProvider)
    }

    @Test
    fun `getNews emits Loading then Success from DB when API succeeds`() = runTest(dispatcher) {
        //arrange
        val dto = listOf(
            ArticleDto("A", "B", "C", "D", "E")
        )
        val entity = dto.map { it.toEntity() }
        val entityFlow = flowOf(entity)

        whenever(networkService.getNews()).thenReturn(NewsResponse(articles = dto))
        whenever(newsDao.getArticles()).thenReturn(entityFlow)

        //act
        val flow = repository.getNews()

        //assert
        flow.test {
            assertEquals(Result.Loading, awaitItem())
            val result = awaitItem()
            assert(result is Result.Success)
            assertEquals((dto.map { it.toDomain() }), (result as Result.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
        verify(newsDao).insertArticles(entity)
    }

    @Test
    fun `getNews emits Loading then Success from DB when API fails`() = runTest(dispatcher) {
        //arrange
        val entity = listOf(
            ArticleEntity("A", "B", "C", "D", "E")
        )
        val entityFlow = flowOf(entity)

        whenever(networkService.getNews()).thenThrow(RuntimeException("API failure"))
        whenever(newsDao.getArticles()).thenReturn(entityFlow)

        //act
        val flow = repository.getNews()

        //assert
        flow.test {
            assertEquals(Result.Loading, awaitItem())
            val result = awaitItem()
            assert(result is Result.Success)
            assertEquals((entity.map { it.toDomain() }), (result as Result.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getNews emits Loading then Success from API when DB insertion fails`() = runTest(dispatcher) {
        //arrange
        val dto = listOf(
            ArticleDto("A", "B", "C", "D", "E")
        )
        val entity = dto.map { it.toEntity() }
        val entityFlow = flowOf<List<ArticleEntity>>(emptyList())

        whenever(networkService.getNews()).thenReturn(NewsResponse(articles = dto))
        whenever(newsDao.insertArticles(entity)).thenThrow(RuntimeException("DB Insertion failed"))
        whenever(newsDao.getArticles()).thenReturn(entityFlow)

        //act
        val flow = repository.getNews()

        //assert
        flow.test {
            assertEquals(Result.Loading, awaitItem())
            val result = awaitItem()
            assert(result is Result.Success)
            assertEquals((dto.map { it.toDomain() }), (result as Result.Success).data)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getNews emits Loading then both API and DB fail`() = runTest(dispatcher) {
        //arrange
        whenever(networkService.getNews()).thenThrow(RuntimeException("API failure"))
        whenever(newsDao.getArticles()).thenThrow(RuntimeException("DB failure"))

        //act
        val flow = repository.getNews()

        //assert
        flow.test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.UnknownError, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        reset(networkService, newsDao)
    }
}