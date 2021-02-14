package com.example.celercompose.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.celercompose.data.PicturesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CelerViewModel @Inject constructor(private val repository: PicturesRepository) : ViewModel() {
    val pictures = liveData {
        emit(repository.requestApi())
    }
}
