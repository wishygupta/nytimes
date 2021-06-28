package com.mine.nytimesapi.data.remote

import com.mine.nytimesapi.data.entities.APIResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface BooksService {
    @GET("lists.json?")
    suspend fun fetchBooks(
        @Query("api-key") apiKey: String,
        @Query("list") bookId: String,
    ): Response<APIResult>
}