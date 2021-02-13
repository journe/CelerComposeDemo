package com.example.celercompose.data

import com.example.celercompose.data.retrofit.RetrofitRequest

class MockPicturesStore() {
    suspend fun request(): List<PictureItem> {
        return RetrofitRequest.apiService.getMockPictures()
    }
}

