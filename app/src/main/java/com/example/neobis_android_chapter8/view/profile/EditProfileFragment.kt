package com.example.neobis_android_chapter8.view.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import com.example.neobis_android_chapter8.databinding.FragmentEditProfileBinding
import com.example.neobis_android_chapter8.utils.Utils
import com.example.neobis_android_chapter8.viewModels.AuthViewModel.ProfileViewModel

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private var PICK_IMAGE_REQUEST  = 1
    private var selectedImageUri: Uri? = null
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hideBtmNav()
        setupNavigation()
        setProfileInfo()
        viewModel.getInfo()
    }

    private fun setupNavigation() {
        binding.cancel.setOnClickListener {
            navigateToProfileFragment()
        }
        binding.ready.setOnClickListener {
            saveData()
            navigateToProfileFragment()
        }
        binding.addPhoto.setOnClickListener {
            choosePhoto()
        }
        binding.addNumber.setOnClickListener {
            saveData()
            navigateToPhoneNumberConfirmation()
        }
    }
    private fun setProfileInfo() {
        viewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.name.setText(it.first_name)
                binding.surname.setText(it.last_name)
                binding.etUsername.setText(it.username)
                binding.birthday.setText(it.birthday)
                binding.etMail.setText(it.email)
                binding.phoneNumber.text = it.phone_number
                profile.photo.let { photoUrl ->
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

    private fun saveData() {
        Utils.name = binding.name.text.toString()
        Utils.surname = binding.surname.text.toString()
        Utils.username = binding.etUsername.text.toString()
        Utils.birthday = binding.birthday.text.toString()
        Utils.email = binding.etMail.text.toString()
        Utils.phoneNumber = binding.phoneNumber.text.toString()
    }

    private fun choosePhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            Utils.selectedImageUri = selectedImageUri
            Glide.with(this).load(selectedImageUri).into(binding.userPhoto)
        }
    }
    private fun navigateToProfileFragment() {
        findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
    }

    private fun navigateToPhoneNumberConfirmation() {
        findNavController().navigate(R.id.action_editProfileFragment_to_addNumberFragment)
    }
}