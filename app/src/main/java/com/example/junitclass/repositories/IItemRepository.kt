package com.example.junitclass.repositories

import androidx.lifecycle.LiveData
import com.example.junitclass.data.local.Item
import com.example.junitclass.data.remote.responses.ImageResponse
import com.example.junitclass.others.Resource
import retrofit2.Response


interface IItemRepository {

    suspend fun insertItem(item: Item)

    suspend fun deleteItem(item: Item)

    fun observeAllItems(): LiveData<List<Item>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchImage(imageQuery: String): Resource<ImageResponse>
}