package com.mondiamedia.ahmedbadr.spokentask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mondiamedia.ahmedbadr.spokentask.R
import com.mondiamedia.ahmedbadr.spokentask.databinding.FragmentProfileBinding
import com.mondiamedia.ahmedbadr.spokentask.models.Album
import com.mondiamedia.ahmedbadr.spokentask.repository.AlbumsRepository
import com.mondiamedia.ahmedbadr.spokentask.repository.UsersRepository
import com.mondiamedia.ahmedbadr.spokentask.view_model.UserViewModel
import kotlinx.coroutines.launch
import java.util.*

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var albumsAdapter: AlbumsAdapter
    private var albums = ArrayList<Album>()

    private val usersRepository = UsersRepository()
    private val albumsRepository = AlbumsRepository()
    private val userViewModel = UserViewModel(usersRepository, albumsRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAlbumsRecyclerView()
        binding.progress.visibility = View.VISIBLE
        userViewModel.user?.observe(viewLifecycleOwner) { user ->
            if (user == null) {
                binding.progress.visibility = View.GONE
                Toast.makeText(activity, R.string.connection_issue, Toast.LENGTH_LONG).show()
                return@observe
            }

            binding.name.text = user.name
            binding.address.text =
                "${user.address.city}, ${user.address.suite}, ${user.address.street}, ${user.address.zipcode}"

            lifecycleScope.launch {
                albums.clear()
                userViewModel.getUserAlbums(user.id)?.let {
                    binding.progress.visibility = View.GONE
                    binding.albumsLabel.visibility = View.VISIBLE
                    albums.addAll(it)
                    albumsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun setupAlbumsRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.albumsRecycler.layoutManager = layoutManager
        albumsAdapter = AlbumsAdapter(this, albums)
        binding.albumsRecycler.adapter = albumsAdapter
        binding.albumsRecycler.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}