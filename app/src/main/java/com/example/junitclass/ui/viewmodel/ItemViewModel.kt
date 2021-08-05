package com.example.junitclass.ui.viewmodel

import androidx.lifecycle.*
import com.example.junitclass.data.local.Item
import com.example.junitclass.data.remote.responses.ImageResponse
import com.example.junitclass.others.Constants
import com.example.junitclass.others.Event
import com.example.junitclass.others.Resource
import com.example.junitclass.repositories.IItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: IItemRepository
) : ViewModel() {

    val items = repository.observeAllItems()
    val totalPrice = repository.observeTotalPrice()

    private val _image = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _image

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: LiveData<String> = _currentImageUrl

    private val _insertItemStatus = MutableLiveData<Event<Resource<Item>>>()
    val insertItemStatus: LiveData<Event<Resource<Item>>> = _insertItemStatus

    fun setCurrentImageUrl(url: String) {
        _currentImageUrl.postValue(url)
    }

    fun deleteItem(item: Item) = viewModelScope.launch {
        repository.deleteItem(item)
    }

    fun insertItemIntoDb(item: Item) = viewModelScope.launch {
        repository.insertItem(item)
    }

    fun insertItem(name: String, amount: String, price: String) {
        if(name.isEmpty() || amount.isEmpty() || price.isEmpty()) {
            _insertItemStatus.postValue(Event(Resource.error("The fields should not be empty", null)))
            return
        }
        if(name.length > Constants.MAX_NAME_LENGTH) {
            _insertItemStatus.postValue(Event(Resource.error("Name should not exceed ${Constants.MAX_NAME_LENGTH} characters", null)))
            return
        }
        if(price.length > Constants.MAX_PRICE_LENGTH) {
            _insertItemStatus.postValue(Event(Resource.error("Price should not exceed ${Constants.MAX_PRICE_LENGTH} characters", null)))
            return
        }
        val amountInt = try {
            amount.toInt()
        } catch (e: Exception) {
            _insertItemStatus.postValue(Event(Resource.error("Please enter a valid amount", null)))
            return
        }
        val item = Item(name, amountInt, price.toFloat(), _currentImageUrl.value ?: "")
        insertItemIntoDb(item)
        setCurrentImageUrl("")
        _insertItemStatus.postValue(Event(Resource.success(item)))
    }

    fun searchImage(imageQuery: String) {
        if(imageQuery.isEmpty()) {
            return
        }
        _image.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchImage(imageQuery)
            _image.value = Event(response)
        }
    }
}