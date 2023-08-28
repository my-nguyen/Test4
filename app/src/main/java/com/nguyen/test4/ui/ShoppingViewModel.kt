package com.nguyen.test4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nguyen.test4.Event
import com.nguyen.test4.Resource
import com.nguyen.test4.data.remote.Result
import com.nguyen.test4.data.room.ShoppingItem
import com.nguyen.test4.repos.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    val items = repository.observeAllShoppingItems()
    val price = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<Result>>>()
    val images: LiveData<Event<Resource<Result>>> = _images
    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl
    private val __insertStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertStatus: LiveData<Event<Resource<ShoppingItem>>> = __insertStatus

    fun setImageUrl(url: String) = _imageUrl.postValue(url)

    fun deleteShoppingItem(item: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(item)
    }

    fun insertShoppingItem(item: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(item)
    }

    fun insertShoppingItem(name: String, amount: String, price: String) {

    }

    fun searchImage(query: String) {

    }
}