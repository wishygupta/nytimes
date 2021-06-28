package com.mine.nytimesapi.data.remote
import javax.inject.Inject

class BestSellerRemoteDataSource @Inject constructor(
    private val bestSellerService: BestSellerService
) : BaseDataSource() {
    suspend fun getBestSellers(apiKey: String) =
        getResult { bestSellerService.fetchBestSellers(apiKey) }
}