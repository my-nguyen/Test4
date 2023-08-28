package com.nguyen.test4.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nguyen.test4.Constants.MAX_NAME_LENGTH
import com.nguyen.test4.Constants.MAX_PRICE_LENGTH
import com.nguyen.test4.Event
import com.nguyen.test4.Resource
import com.nguyen.test4.data.remote.Result
import com.nguyen.test4.data.room.ShoppingItem
import com.nguyen.test4.repos.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
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
        if (name.isEmpty() || amount.isEmpty() || price.isEmpty()) {
            __insertStatus.postValue(Event(Resource.error("The fields must not be empty", null)))
            return
        }
        if (name.length > MAX_NAME_LENGTH) {
            __insertStatus.postValue(Event(Resource.error("The name of the item must not exceed $MAX_NAME_LENGTH characters", null)))
            return
        }
        if (price.length > MAX_PRICE_LENGTH) {
            __insertStatus.postValue(Event(Resource.error("The price of the item must not exceed $MAX_PRICE_LENGTH characters", null)))
            return
        }
        val amt = try {
            amount.toInt()
        } catch (e: Exception) {
            __insertStatus.postValue(Event(Resource.error("Please enter a valid amount", null)))
            return
        }

        val item = ShoppingItem(name, amt, price.toFloat(), _imageUrl.value ?: "")
        insertShoppingItem(item)
        setImageUrl("")
        __insertStatus.postValue(Event(Resource.success(item)))
    }

    fun searchImage(query: String) {
        if (query.isEmpty())
            return

        _images.value = Event(Resource.loading(null))
        viewModelScope.launch {
            val response = repository.searchImage(query)
            _images.value = Event(response)
        }
    }
}