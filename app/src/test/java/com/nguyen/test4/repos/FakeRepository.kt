package com.nguyen.test4.repos

import androidx.lifecycle.MutableLiveData
import com.nguyen.test4.Resource
import com.nguyen.test4.data.remote.Result
import com.nguyen.test4.data.room.ShoppingItem

class FakeRepository : Repository {
    private val items = mutableListOf<ShoppingItem>()
    private val allShoppingItems = MutableLiveData<List<ShoppingItem>>(items)
    private val totalPrice = MutableLiveData<Float>()
    var isNetworkError = false

    override suspend fun insertShoppingItem(item: ShoppingItem) {
        items.add(item)
        refresh()
    }

    override suspend fun deleteShoppingItem(item: ShoppingItem) {
        items.remove(item)
        refresh()
    }

    override fun observeAllShoppingItems() = allShoppingItems

    override fun observeTotalPrice() = totalPrice

    override suspend fun searchImage(query: String): Resource<Result> {
        if (isNetworkError)
            return Resource.error("Error", null)
        return Resource.success(Result(listOf(), 0, 0))
    }

    private fun refresh() {
        allShoppingItems.postValue(items)
        totalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice() = items.sumOf { it.price.toDouble() }.toFloat()
}