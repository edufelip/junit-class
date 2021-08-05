package com.example.junitclass.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.junitclass.data.local.Item
import com.example.junitclass.data.remote.responses.ImageResponse
import com.example.junitclass.others.Resource

class FakeItemRepository: IItemRepository {

    private val items = mutableListOf<Item>()
    private val observableItems = MutableLiveData<List<Item>>(items)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLivedata() {
        observableItems.postValue(items)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return items.sumOf { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertItem(item: Item) {
        items.add(item)
        refreshLivedata()
    }

    override suspend fun deleteItem(item: Item) {
        items.remove(item)
        refreshLivedata()
    }

    override fun observeAllItems(): LiveData<List<Item>> {
        return observableItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }


}