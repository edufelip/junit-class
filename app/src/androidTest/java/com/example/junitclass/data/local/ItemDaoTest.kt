package com.example.junitclass.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.junitclass.getOrAwaitValue
import com.example.junitclass.launchFragmentInHiltContainer
import com.example.junitclass.ui.fragments.CartFragment
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ItemDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ItemDatabase
    private lateinit var dao: ItemDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.itemDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCartItem() =  runBlockingTest {
        val item = Item( "name", 1, 1f, "url", 1)
        dao.insertItem(item)
        val itemList = dao.observeItems().getOrAwaitValue()

        assertThat(itemList).contains(item)
    }

    @Test
    fun deleteCartItem() = runBlockingTest {
        val item = Item("name", 1, 1f, "url")
        dao.insertItem(item)
        dao.deleteItem(item)
        val itemList = dao.observeItems().getOrAwaitValue()

        assertThat(itemList).doesNotContain(item)
    }

    @Test
    fun observePriceSum() = runBlockingTest {
        val itemOne = Item("name", 1, 1f, "url")
        val itemTwo = Item("name", 1, 1f, "url")
        val itemThree = Item( "name", 1, 1f, "url")
        dao.insertItem(itemOne)
        dao.insertItem(itemTwo)
        dao.insertItem(itemThree)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(3f)
    }
}