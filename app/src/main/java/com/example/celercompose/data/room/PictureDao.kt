package com.example.celercompose.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    @Query("SELECT * FROM PictureItem ORDER BY id")
    fun getPictures(): Flow<List<PictureItem>>

    @Insert
    suspend fun insertPicture(pictureItem: PictureItem): Long

    @Delete
    suspend fun deletePicture(pictureItem: PictureItem)

}
