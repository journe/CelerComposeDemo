package com.example.celercompose.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureItem(
    @PrimaryKey
    val id: Int,
    val imageUrl: String,
    val videoUrl: String
) {
}