package com.nguyen.test4.repos

import androidx.lifecycle.LiveData
import com.nguyen.test4.Resource
import com.nguyen.test4.data.remote.Result
import com.nguyen.test4.data.room.ShoppingItem

interface Repository {
    suspend fun insertShoppingItem(item: ShoppingItem)

    suspend fun deleteShoppingItem(item: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchImage(query: String): Resource<Result>
}