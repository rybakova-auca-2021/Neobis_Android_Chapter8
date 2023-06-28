package com.example.neobis_android_chapter8.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentProfileBinding
import com.example.neobis_android_chapter8.utils.ProfileInfo
import com.example.neobis_android_chapter8.utils.Utils
import com.example.neobis_android_chapter8.viewModels.AuthViewModel.ProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =  FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).show()
        profileViewModel.getInfo(this)
        setupNavigation()
        setData()
    }

    private fun setupNavigation() {
        binding.change.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.likedText.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_likedProductsFragment)
        }
        binding.productsText.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_myProductsFragment)
        }
    }

    private fun setData() {
        val username = Utils.username
        binding.name.text = username
        ProfileInfo.selectedImageUri?.let { imageUri ->
            Glide.with(this).load(imageUri).into(binding.userPhoto)
        }
    }
}