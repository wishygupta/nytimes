package com.mine.nytimesapi.data.remote

import com.mine.nytimesapi.data.entities.APIResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface BestSellerService {
    @GET("lists/names.json?")
    suspend fun fetchBestSellers(@Query("api-key") apiKey: String): Response<APIResult>
}