package com.mondiamedia.ahmedbadr.spokentask.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mondiamedia.ahmedbadr.spokentask.databinding.AlbumItemBinding
import com.mondiamedia.ahmedbadr.spokentask.models.Album

class AlbumsAdapter(
    private val fragment: Fragment,
    private val albums: List<Album>
) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val binding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        val album = albums[position]
        holder.binding.albumName.text = album.title
        holder.itemView.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToAlbumsFragment(album.id, album.title)
            findNavController(fragment).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    inner class AlbumsViewHolder(val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}