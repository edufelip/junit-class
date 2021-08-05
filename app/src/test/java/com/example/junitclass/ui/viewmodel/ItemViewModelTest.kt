package com.example.junitclass.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.junitclass.MainCoroutineRule
import com.example.junitclass.getOrAwaitValueTest
import com.example.junitclass.others.Constants
import com.example.junitclass.others.Status
import com.example.junitclass.repositories.FakeItemRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ItemViewModelTest {
    private lateinit var viewModel: ItemViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = ItemViewModel(FakeItemRepository())
    }

    @Test
    fun `insert shopping item with empty field, returns error`() {
        viewModel.insertItem("name", "", "3.0")

        val value = viewModel.insertItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name, returns error`() {
        val string = buildString {
            for(i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertItem(string, "5", "3.0")

        val value = viewModel.insertItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long price, returns error`() {
        val string = buildString {
            for(i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }
        viewModel.insertItem("name", "5", string)

        val value = viewModel.insertItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`() {
        viewModel.insertItem("name", "9999999999999999999", "3.0")

        val value = viewModel.insertItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input, returns success`() {
        viewModel.insertItem("name", "5", "3.0")

        val value = viewModel.insertItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}