package com.nguyen.test4.repos

import com.nguyen.test4.Resource
import com.nguyen.test4.data.remote.PixarBayService
import com.nguyen.test4.data.remote.Result
import com.nguyen.test4.data.room.ShoppingDao
import com.nguyen.test4.data.room.ShoppingItem
import javax.inject.Inject

class DefaultRepository @Inject constructor(private val dao: ShoppingDao, private val service: PixarBayService) :
    Repository {
    override suspend fun insertShoppingItem(item: ShoppingItem) = dao.insertShoppingItem(item)

    override suspend fun deleteShoppingItem(item: ShoppingItem) = dao.deleteShoppingItem(item)

    override fun observeAllShoppingItems() = dao.observeAllShoppingItems()

    override fun observeTotalPrice() = dao.observeTotalPrice()

    override suspend fun searchImage(query: String): Resource<Result> {
        try {
            val response = service.searchImage(query)
            if (response.isSuccessful) {
                if (response.body() != null)
                    return Resource.success(response.body())
                return Resource.error("An unknown error occurred", null)
            }
            return Resource.error("An unknown error occurred", null)
        } catch (e: Exception) {
            return Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}