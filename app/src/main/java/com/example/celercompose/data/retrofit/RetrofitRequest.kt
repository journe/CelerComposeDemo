package com.example.celercompose.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitRequest {
    val apiService: RetrofitService
    private val client: OkHttpClient = OkHttpClient.Builder()
        .apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            followRedirects(false)
        }
        .build()

    init {
        apiService = Retrofit.Builder()
            .baseUrl("http://private-04a55-videoplayer1.apiary-mock.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

}