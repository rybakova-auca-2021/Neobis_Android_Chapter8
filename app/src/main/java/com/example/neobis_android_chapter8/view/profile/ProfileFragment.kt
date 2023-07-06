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
import com.example.neobis_android_chapter8.viewModels.AuthViewModel.ProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =  FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).showBtmNav()
        viewModel.getInfo()
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
        viewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.name.text = it.username
                if (it.photo.isNullOrEmpty()) {
                    Glide.with(this).load(R.drawable.user_photo).into(binding.userPhoto)
                } else {
                    val photoUrl = "http://16.16.200.195${it.photo}"
                    Glide.with(this).load(photoUrl).circleCrop().into(binding.userPhoto)
                }
            }
        }
    }
}