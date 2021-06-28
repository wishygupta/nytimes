package com.mine.nytimesapi.ui.bestselller

import androidx.lifecycle.*
import com.mine.nytimesapi.data.entities.toBestSellers
import com.mine.nytimesapi.data.remote.BestSellerRemoteDataSource
import com.mine.nytimesapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class BestSellerViewModel @Inject constructor(
    private val remoteData: BestSellerRemoteDataSource
) : ViewModel() {


    fun fetchImages(key: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val data = remoteData.getBestSellers(key).data?.results?.map { it.toBestSellers() }
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}