package com.example.neobis_android_chapter8.view.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentEditProfileBinding
import com.example.neobis_android_chapter8.utils.ProfileInfo
import com.example.neobis_android_chapter8.utils.Utils

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private var PICK_IMAGE_REQUEST  = 1
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()
        setProfileInfo()
        setupNavigation()
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
        binding.name.setText(ProfileInfo.name)
        binding.surname.setText(ProfileInfo.surname)
        binding.etUsername.setText(ProfileInfo.username)
        binding.birthday.setText(ProfileInfo.birthday)
        binding.etMail.setText(ProfileInfo.email)
        binding.phoneNumber.text = ProfileInfo.phoneNumber
        binding.userPhoto.setImageURI(ProfileInfo.selectedImageUri)
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
            println(Utils.selectedImageUri)
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