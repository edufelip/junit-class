package com.example.junitclass.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.junitclass.data.local.Item
import com.example.junitclass.getOrAwaitValue
import com.example.junitclass.launchFragmentInHiltContainer
import com.example.junitclass.repositories.FakeItemRepositoryAndroidTest
import com.example.junitclass.ui.TestMainFragmentFactory
import com.example.junitclass.ui.viewmodel.ItemViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import javax.inject.Inject
import com.example.junitclass.R


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddItemFragmentTest{

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
    fun clickInsertIntoDb__itemInserted() {
        val testViewModel = ItemViewModel(FakeItemRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddItemFragment> (
            fragmentFactory = testFragmentFactory
        ) {
           viewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("Random Item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.6"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.items.getOrAwaitValue {})
            .contains(Item("Random Item", 5, 5.6f, ""))
    }

    @Test
    fun pressBackButton__navigateBack() {
        lateinit var navController: NavController
        launchFragmentInHiltContainer<ImagePickFragment> (
            fragmentFactory = testFragmentFactory
        ) {
            val activity = requireActivity()
            val view = requireView()
            navController = TestNavHostController(activity)
            activity.runOnUiThread { navController.setGraph(R.navigation.nav_graph) }
            Navigation.setViewNavController(view, navController)
        }
        navController.navigateUp()
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.mainFragment)
    }
}