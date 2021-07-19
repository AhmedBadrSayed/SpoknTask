package com.mondiamedia.ahmedbadr.spokentask.repository

import com.mondiamedia.ahmedbadr.spokentask.api.AlbumsInterface
import com.mondiamedia.ahmedbadr.spokentask.api.RemoteDataSource
import com.mondiamedia.ahmedbadr.spokentask.models.Album
import com.mondiamedia.ahmedbadr.spokentask.models.Photo

class AlbumsRepository {
    private val remoteDataSource = RemoteDataSource()
    private val albumsService =
        remoteDataSource.getService(AlbumsInterface::class.java) as AlbumsInterface

    suspend fun getUserAlbums(userId: Int): List<Album>? {
        return try {
            albumsService.getUserAlbums(userId)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getAlbumPhotos(albumId: Int): List<Photo>? {
        return try {
            albumsService.getAlbumPhotos(albumId)
        } catch (e: Exception) {
            null
        }
    }
}