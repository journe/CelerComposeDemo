package com.example.celercompose.ui


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.celercompose.data.PicturesRepository
import com.example.celercompose.data.room.PictureItem
import com.example.celercompose.test.PreviewPictures
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CelerViewModel @Inject constructor(private val repository: PicturesRepository) : ViewModel() {

    val selected = MutableLiveData(PreviewPictures[0])
    val pictures = liveData {
        emit(repository.requestApi())
    }

    fun onSelected(item: PictureItem) {
        selected.value = item
    }

}
