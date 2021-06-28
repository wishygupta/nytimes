package com.mine.nytimesapi.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mine.nytimesapi.data.remote.BestSellerRemoteDataSource
import com.mine.nytimesapi.data.remote.BestSellerService
import com.mine.nytimesapi.data.remote.BooksRemoteDataSource
import com.mine.nytimesapi.data.remote.BooksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor).build()


    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com/svc/books/v3/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideBestSellerService(retrofit: Retrofit): BestSellerService =
        retrofit.create(BestSellerService::class.java)

    @Singleton
    @Provides
    fun provideBestSellerRemoteDataSource(service: BestSellerService) =
        BestSellerRemoteDataSource(service)

    @Provides
    fun provideBooksService(retrofit: Retrofit): BooksService =
        retrofit.create(BooksService::class.java)

    @Singleton
    @Provides
    fun provideBooksRemoteDataSource(service: BooksService) =
        BooksRemoteDataSource(service)


}