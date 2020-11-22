package com.example.newzy.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsService {

    @Headers(
        "x-bingapis-sdk: true",
        "x-rapidapi-key: mfgLUdOEHNmshsARhkXZlH5FSfNbp1x61p3jsnwLBWcnlpqneo",
        "x-rapidapi-host:bing-news-search1.p.rapidapi.com"
    )
    @GET("news?safeSearch=Off&textFormat=Raw")
    suspend fun getRepos(): RepoNewsResponse

    companion object {
        private const val BASE_URL =
            "https://bing-news-search1.p.rapidapi.com/"

        fun create(): NewsService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService::class.java)
        }
    }
}