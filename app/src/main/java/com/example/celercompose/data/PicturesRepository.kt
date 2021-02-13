package com.example.celercompose.data

import com.example.celercompose.api.RetrofitService
import com.example.celercompose.data.room.AppDatabase
import com.example.celercompose.data.room.PictureItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicturesRepository @Inject constructor(
    private val apiService: RetrofitService,
    private val appDatabase: AppDatabase
) {
    suspend fun requestApi(): List<PictureItem> {
        return apiService.getMockPictures()
    }

    fun getAllFromLocal(): Flow<List<PictureItem>> {
        return appDatabase.pictureDao().getPictures()
    }

    suspend fun insertPicture(pictureItem: PictureItem) {
        appDatabase.pictureDao().insertPicture(pictureItem = pictureItem)
    }

    suspend fun deletePicture(pictureItem: PictureItem) {
        appDatabase.pictureDao().deletePicture(pictureItem = pictureItem)
    }
}

