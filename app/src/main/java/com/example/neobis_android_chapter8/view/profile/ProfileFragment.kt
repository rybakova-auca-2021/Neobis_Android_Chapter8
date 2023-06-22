package com.example.neobis_android_chapter8.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.utils.Utils
import com.example.neobis_android_chapter8.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
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
        setData()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.change.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        // TODO - navigation to products and liked page
    }

    private fun setData() {
        val username = Utils.username
        binding.name.text = username
        Utils.selectedImageUri?.let { imageUri ->
            Glide.with(this).load(imageUri).into(binding.userPhoto)
        }
    }
}