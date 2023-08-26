package com.nguyen.test4.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}