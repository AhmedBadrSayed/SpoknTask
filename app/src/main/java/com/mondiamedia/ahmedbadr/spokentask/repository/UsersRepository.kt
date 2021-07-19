package com.mondiamedia.ahmedbadr.spokentask.repository

import User
import com.mondiamedia.ahmedbadr.spokentask.api.RemoteDataSource
import com.mondiamedia.ahmedbadr.spokentask.api.UserInterface

class UsersRepository {
    private val remoteDataSource = RemoteDataSource()
    private val userService =
        remoteDataSource.getService(UserInterface::class.java) as UserInterface

    suspend fun getUser(id: String): User? {
        return try {
            userService.getUser(id)
        } catch (e: Exception) {
            null
        }
    }

}