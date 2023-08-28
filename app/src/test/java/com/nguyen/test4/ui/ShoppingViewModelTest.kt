package com.nguyen.test4.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nguyen.test4.Constants.MAX_NAME_LENGTH
import com.nguyen.test4.Constants.MAX_PRICE_LENGTH
import com.nguyen.test4.MainDispatcherRule
import com.nguyen.test4.Status
import com.nguyen.test4.getOrAwaitValueTest
import com.nguyen.test4.repos.FakeRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ShoppingViewModelTest {
    private lateinit var viewModel: ShoppingViewModel

    @get:Rule
    var executorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeRepository())
    }

    @Test
    fun `insert shopping item with empty amount, returns error`() {
        viewModel.insertShoppingItem("Coke", "", "2.0")
        val value = viewModel.insertStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with name too long, returns error`() {
        viewModel.insertShoppingItem("a".repeat(MAX_NAME_LENGTH + 1), "5", "2.0")
        val value = viewModel.insertStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with price too long, returns error`() {
        viewModel.insertShoppingItem("Coke", "5", "1".repeat(MAX_PRICE_LENGTH + 1))
        val value = viewModel.insertStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with amount too high, returns error`() {
        viewModel.insertShoppingItem("Coke", "55555555555555555555555555555555", "2.0")
        val value = viewModel.insertStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, returns success`() {
        viewModel.insertShoppingItem("Coke", "5", "2.0")
        val value = viewModel.insertStatus.getOrAwaitValueTest()
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}