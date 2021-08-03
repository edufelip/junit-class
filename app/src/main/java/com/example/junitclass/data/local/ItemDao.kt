package com.example.junitclass.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: Item)

    @Delete
    suspend fun deleteCartItem(item: Item)

    @Query("SELECT * FROM items")
    fun observeAllItems(): LiveData<List<Item>>

    @Query("SELECT SUM(price * quantity) FROM items")
    fun observeTotalPrice(): LiveData<Float>
}