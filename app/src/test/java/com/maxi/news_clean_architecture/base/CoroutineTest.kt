package com.maxi.news_clean_architecture.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

/**
 * Base Coroutine Test class, not using as of now!
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class CoroutineTest {

    protected val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher) //override Dispatchers.Main with test dispatcher
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain() //restore the original main dispatcher
    }
}