package com.mondiamedia.ahmedbadr.spokentask.view_model

import User
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mondiamedia.ahmedbadr.spokentask.models.Album
import com.mondiamedia.ahmedbadr.spokentask.repository.AlbumsRepository
import com.mondiamedia.ahmedbadr.spokentask.repository.UsersRepository
import kotlinx.coroutines.launch

class UserViewModel(private val usersRepository: UsersRepository,
                    private val albumsRepository: AlbumsRepository) : ViewModel() {
    private var _user = MutableLiveData<User>()
    val user: LiveData<User>?
        get() = _user

    fun init() {
        viewModelScope.launch {
            user?.value.let {
                if (it != null) return@launch
            }

            _user.value = usersRepository.getUser("5")
        }
    }

    suspend fun getUserAlbums(userId: Int) : List<Album>? {
        return albumsRepository.getUserAlbums(userId)
    }

}