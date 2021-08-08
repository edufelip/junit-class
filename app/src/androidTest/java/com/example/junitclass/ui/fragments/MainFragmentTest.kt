package com.example.junitclass.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.junitclass.R
import com.example.junitclass.adapters.CartAdapter
import com.example.junitclass.data.local.Item
import com.example.junitclass.getOrAwaitValue
import com.example.junitclass.launchFragmentInHiltContainer
import com.example.junitclass.ui.TestMainFragmentFactory
import com.example.junitclass.ui.viewmodel.ItemViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testFragmentFactory: TestMainFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun swipeShoppingItem__deleteItemFromDb() {
        val item = Item("Random Item", 1, 1f, "", 1)
        var testViewModel: ItemViewModel? = null
        launchFragmentInHiltContainer<MainFragment> (
            fragmentFactory = testFragmentFactory
        ) {
            testViewModel = viewModel
            viewModel?.insertItemIntoDb(item)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CartAdapter.CartViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.items?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddItemButton__navigateToAddItemFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MainFragment> (
            fragmentFactory = testFragmentFactory
        ){
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.floatingActionButton)).perform(click())

        verify(navController).navigate(
            MainFragmentDirections.actionMainFragmentToAddItemFragment()
        )
    }
}