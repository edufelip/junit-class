package com.example.junitclass.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.junitclass.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ItemDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ItemDatabase
    private lateinit var dao: ItemDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ItemDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.itemDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCartItem() =  runBlockingTest {
        val item = Item(1, "name", 1, 1f, "url")
        dao.insertCartItem(item)
        val itemList = dao.observeAllItems().getOrAwaitValue()

        assertThat(itemList).contains(item)
    }

    @Test
    fun deleteCartItem() = runBlockingTest {
        val item = Item(1, "name", 1, 1f, "url")
        dao.insertCartItem(item)
        dao.deleteCartItem(item)
        val itemList = dao.observeAllItems().getOrAwaitValue()

        assertThat(itemList).doesNotContain(item)
    }

    @Test
    fun observePriceSum() = runBlockingTest {
        val itemOne = Item(1, "name", 1, 1f, "url")
        val itemTwo = Item(2, "name", 1, 1f, "url")
        val itemThree = Item(3, "name", 1, 1f, "url")
        dao.insertCartItem(itemOne)
        dao.insertCartItem(itemTwo)
        dao.insertCartItem(itemThree)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(3f)
    }
}