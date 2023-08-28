package com.nguyen.test4.di

import android.content.Context
import androidx.room.Room
import com.nguyen.test4.data.room.ShoppingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named("test_db")
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}