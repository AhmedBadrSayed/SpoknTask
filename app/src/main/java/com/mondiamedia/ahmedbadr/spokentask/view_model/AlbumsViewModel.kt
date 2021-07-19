package com.mondiamedia.ahmedbadr.spokentask.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mondiamedia.ahmedbadr.spokentask.models.Photo
import com.mondiamedia.ahmedbadr.spokentask.repository.AlbumsRepository
import kotlinx.coroutines.launch

class AlbumsViewModel(private val albumsRepository: AlbumsRepository) : ViewModel() {
    private var _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>?
        get() = _photos

    fun getAlbumPhotos(albumId: Int) {
        viewModelScope.launch {
            photos?.value.let {
                if (it != null) return@launch
            }

            _photos.value = albumsRepository.getAlbumPhotos(albumId)
        }
    }
}