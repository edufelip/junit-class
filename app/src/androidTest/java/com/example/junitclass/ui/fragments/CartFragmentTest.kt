package com.example.junitclass.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.junitclass.R
import com.example.junitclass.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CartFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddItemButton__navigateToAddItemFragment() {
        val navController = mock(NavController::class.java)
        //`when`(navController.popBackStack()).thenReturn(false)
        //modify behavior in mock
        launchFragmentInHiltContainer<CartFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.floatingActionButton)).perform(click())

        verify(navController).navigate(
            CartFragmentDirections.actionCartFragmentToAddItemFragment()
        )
    }
}