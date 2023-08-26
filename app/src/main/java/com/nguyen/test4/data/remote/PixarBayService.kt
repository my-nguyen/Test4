package com.nguyen.test4.data.remote

import com.nguyen.test4.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixarBayService {
    @GET("/api/")
    suspend fun searchImage(@Query("q") query: String, @Query("key") apiKey: String = BuildConfig.API_KEY): Response<Result>
}