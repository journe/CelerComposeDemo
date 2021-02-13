package com.example.celercompose.data.retrofit

import com.example.celercompose.data.PictureItem
import retrofit2.http.GET

interface RetrofitService {
    @GET("/pictures")
    suspend fun getMockPictures(): List<PictureItem>
}