package com.example.junitclass.repositories

import androidx.lifecycle.LiveData
import com.example.junitclass.data.local.Item
import com.example.junitclass.data.local.ItemDao
import com.example.junitclass.data.remote.PixaBayAPI
import com.example.junitclass.data.remote.responses.ImageResponse
import com.example.junitclass.others.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultItemRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val pixaBayAPI: PixaBayAPI
): IItemRepository {
    override suspend fun insertItem(item: Item) {
        itemDao.insertItem(item)
    }

    override suspend fun deleteItem(item: Item) {
        itemDao.deleteItem(item)
    }

    override fun observeAllItems(): LiveData<List<Item>> {
        return itemDao.observeItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return itemDao.observeTotalPrice()
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixaBayAPI.searchImage(imageQuery)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An error occured", null)
            } else {
                Resource.error("An error occured", null)
            }
        } catch (e: Exception) {
            Resource.error("Can't reach the server", null)
        }
    }

}