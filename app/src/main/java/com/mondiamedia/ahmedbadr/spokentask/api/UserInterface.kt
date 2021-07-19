package com.mondiamedia.ahmedbadr.spokentask.api

import User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserInterface {
    @GET("/users")
    suspend fun getUsers(): List<User>

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") userId: String): User?
}