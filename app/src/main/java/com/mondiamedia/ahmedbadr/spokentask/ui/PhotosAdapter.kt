package com.mondiamedia.ahmedbadr.spokentask.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mondiamedia.ahmedbadr.spokentask.databinding.PhotoItemBinding
import com.mondiamedia.ahmedbadr.spokentask.models.Photo

class PhotosAdapter(
    private val photos: List<Photo>
) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo = photos[position]
        holder.binding.albumPhoto.load(photo.url) {
            crossfade(true)
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class PhotosViewHolder(val binding: PhotoItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}