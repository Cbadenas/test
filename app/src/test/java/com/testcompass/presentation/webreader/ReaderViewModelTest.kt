package com.testcompass.presentation.webreader

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test

class ReaderViewModelTest {

    private lateinit var viewModel: ReaderViewModel
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ReaderViewModel(testDispatcher)
    }

    @Test
    fun `given a website url, viewmodel should return the website data`() = runTest {
        withContext(testDispatcher) {
            viewModel.onAction(ReaderActions.ReadWebsite("https://www.compass.com/about/"))
        }

        assert(viewModel.state.value.website10s.isNotEmpty())
    }

    @Test
    fun `given a website url, viewmodel should return the unique words data`() = runTest {
        withContext(testDispatcher) {
            viewModel.onAction(ReaderActions.ReadWebsite("https://www.compass.com/about/"))
        }

        assert(viewModel.state.value.uniques > 0)
    }
}