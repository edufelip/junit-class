package com.example.junitclass.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: Item)

    @Delete
    fun deleteItem(item: Item)

    @Query("SELECT * FROM items")
    fun observeItems(): LiveData<List<Item>>

    @Query("SELECT SUM(price * quantity) FROM items")
    fun observeTotalPrice(): LiveData<Float>
}