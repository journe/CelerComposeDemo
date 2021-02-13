package com.example.celercompose.api

import com.example.celercompose.data.room.PictureItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface RetrofitService {
    companion object {
        private const val BASE_URL = "http://private-04a55-videoplayer1.apiary-mock.com"

        fun create(): RetrofitService {
            val client: OkHttpClient = OkHttpClient.Builder()
                .apply {
                    connectTimeout(10, TimeUnit.SECONDS)
                    writeTimeout(10, TimeUnit.SECONDS)
                    readTimeout(30, TimeUnit.SECONDS)
                    followRedirects(false)
                }
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }

    @GET("/pictures")
    suspend fun getMockPictures(): List<PictureItem>
}