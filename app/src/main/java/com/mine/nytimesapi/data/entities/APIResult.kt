package com.mine.nytimesapi.data.entities

import com.google.gson.annotations.SerializedName
data class APIResult(
    @SerializedName("results")
    val results: List<ServerBestSellers>
)

data class ServerBestSellers(
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("list_name_encoded")
    val listNameEncoded: String,
    @SerializedName("newest_published_date")
    val newestPublishedDate: String,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("book_details")
    val bookDetails: List<ServerPopularBooks>
)

fun ServerBestSellers.toBestSellers(): BestSellers {
    return BestSellers(
        name = displayName,
        encodedName = listNameEncoded,
        publishDate = newestPublishedDate,
        updated = updated
    )
}

data class ServerPopularBooks(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("primary_isbn10")
    val isbn10: Int
)

fun ServerPopularBooks.toPopularBooks(): PopularBooks {
    return PopularBooks(
        title = title,
        description = description,
        author = author,
        price = price
    )
}