package com.example.junitclass.data.remote

import com.example.junitclass.BuildConfig
import com.example.junitclass.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaBayAPI {
    @GET("/api/")
    suspend fun searchImage(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.PIXABAY_API_KEY
    ): Response<ImageResponse>
}