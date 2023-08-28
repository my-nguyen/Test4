package com.nguyen.test4.di

import android.content.Context
import androidx.room.Room
import com.nguyen.test4.Constants.BASE_URL
import com.nguyen.test4.Constants.DATABASE_NAME
import com.nguyen.test4.data.remote.PixarBayService
import com.nguyen.test4.data.room.ShoppingDao
import com.nguyen.test4.data.room.ShoppingDatabase
import com.nguyen.test4.repos.DefaultRepository
import com.nguyen.test4.repos.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ShoppingDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(database: ShoppingDatabase) = database.shoppingDao()

    @Singleton
    @Provides
    fun provideService() =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixarBayService::class.java)

    @Singleton
    @Provides
    fun provideRepository(dao: ShoppingDao, service: PixarBayService) = DefaultRepository(dao, service) as Repository
}