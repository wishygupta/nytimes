package com.mine.nytimesapi.data.remote
import javax.inject.Inject

class BooksRemoteDataSource @Inject constructor(
    private val booksService: BooksService
) : BaseDataSource() {
    suspend fun getBooks(apiKey: String, bookId: String) =
        getResult { booksService.fetchBooks(apiKey, bookId) }
}