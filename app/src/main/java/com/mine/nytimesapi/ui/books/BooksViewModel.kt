package com.mine.nytimesapi.ui.books

import androidx.lifecycle.*
import com.mine.nytimesapi.data.entities.toPopularBooks
import com.mine.nytimesapi.data.remote.BooksRemoteDataSource
import com.mine.nytimesapi.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val remoteData: BooksRemoteDataSource
) : ViewModel() {

    fun fetchBooks(key: String, bookId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val data = remoteData.getBooks(
                key,
                bookId
            ).data?.results?.flatMap { it.bookDetails.map { it.toPopularBooks() } }
            emit(Resource.success(data = data))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}