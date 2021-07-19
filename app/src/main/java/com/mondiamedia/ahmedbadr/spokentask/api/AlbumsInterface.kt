package com.mondiamedia.ahmedbadr.spokentask.api

import com.mondiamedia.ahmedbadr.spokentask.models.Album
import com.mondiamedia.ahmedbadr.spokentask.models.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumsInterface {
    @GET("/albums")
    suspend fun getUserAlbums(@Query("userId") userId: Int): List<Album>?

    @GET("/photos")
    suspend fun getAlbumPhotos(@Query("albumId") albumId: Int): List<Photo>?
}