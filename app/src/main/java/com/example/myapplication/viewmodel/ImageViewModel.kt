package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.Images
import com.example.myapplication.domain.usecase.GetImageListUseCase
import com.example.myapplication.domain.usecase.GetImageUriUseCase
import com.example.myapplication.domain.usecase.LoadImageListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ImageViewModel(
    private val getImageListUseCase: GetImageListUseCase,
    private val getImageUriUseCase: GetImageUriUseCase,
    private val loadImageListUseCase: LoadImageListUseCase
) : ViewModel() {
    private val _imageList = MutableLiveData<ArrayList<Images>>()
    val imageList: LiveData<ArrayList<Images>>
        get() = _imageList

    init {
        _imageList.value = getImageListUseCase.execute()
    }

    fun loadImageList() = CoroutineScope(Dispatchers.IO).launch {
        loadImageListUseCase.execute()
        withContext(Dispatchers.Main) {
            _imageList.value = getImageListUseCase.execute()
        }
    }

    fun getImageUri(position: Int): String {
        return getImageUriUseCase.execute(position)
    }
}
