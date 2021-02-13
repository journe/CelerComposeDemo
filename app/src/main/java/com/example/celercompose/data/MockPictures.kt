package com.example.celercompose.data

class MockPictures : ArrayList<PictureItem>()

data class PictureItem(
    val id: Int,
    val imageUrl: String,
    val videoUrl: String
) {
}