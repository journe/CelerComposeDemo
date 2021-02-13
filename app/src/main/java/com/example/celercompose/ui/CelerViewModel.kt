package com.example.celercompose.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.celercompose.data.MockPicturesStore
import com.example.celercompose.data.PictureItem
import com.example.celercompose.test.PreviewPictures

class CelerViewModel() : ViewModel() {
    val selected = MutableLiveData(PreviewPictures[0])
    val pictures = liveData {
        emit(MockPicturesStore().request())
    }

    fun onSelected(item: PictureItem) {
        selected.value = item
    }

}
