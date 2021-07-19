package com.mondiamedia.ahmedbadr.spokentask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.mondiamedia.ahmedbadr.spokentask.R
import com.mondiamedia.ahmedbadr.spokentask.databinding.FragmentAlbumsBinding
import com.mondiamedia.ahmedbadr.spokentask.models.Photo
import com.mondiamedia.ahmedbadr.spokentask.repository.AlbumsRepository
import com.mondiamedia.ahmedbadr.spokentask.view_model.AlbumsViewModel
import java.util.*

class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val args: AlbumsFragmentArgs by navArgs()

    private lateinit var photosAdapter: PhotosAdapter
    private var photos = ArrayList<Photo>()
    private var filteredPhotos = ArrayList<Photo>()

    private val albumsRepository = AlbumsRepository()
    private val albumsViewModel = AlbumsViewModel(albumsRepository)

    private lateinit var albumName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val albumId = args.albumId
        albumName = args.albumName
        albumsViewModel.getAlbumPhotos(albumId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPhotosRecyclerView()
        binding.albumTitle.text = albumName
        binding.progress.visibility = View.VISIBLE
        photos.clear()
        albumsViewModel.photos?.observe(viewLifecycleOwner) {
            binding.progress.visibility = View.GONE

            if (it == null) {
                Toast.makeText(activity, R.string.connection_issue, Toast.LENGTH_LONG).show()
                return@observe
            }

            photos.addAll(it)
            photosAdapter.notifyDataSetChanged()
        }

        binding.searchBar.setIconifiedByDefault(false)
        binding.searchBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                filteredPhotos.clear()
                filteredPhotos.addAll(photos.filter {
                    it.title.contains(newText, true)
                })
                photosAdapter = PhotosAdapter(filteredPhotos)
                binding.photosRecycler.adapter = photosAdapter
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })
    }

    private fun setupPhotosRecyclerView() {
        val layoutManager = GridLayoutManager(activity, 3)
        binding.photosRecycler.layoutManager = layoutManager
        photosAdapter = PhotosAdapter(photos)
        binding.photosRecycler.adapter = photosAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}