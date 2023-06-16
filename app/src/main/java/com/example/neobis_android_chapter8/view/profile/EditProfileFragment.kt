package com.example.neobis_android_chapter8.view.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.Utils
import com.example.neobis_android_chapter8.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private var isDataChanged = false
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
        setData()
        setupNavigation()
        setupListeners()
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
            navigateToPhoneNumberConfirmation()
        }
    }

    private fun setupListeners() {
        binding.name.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isDataChanged = true
                showSaveButton()
            }
        }
        binding.surname.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isDataChanged = true
                showSaveButton()
            }
        }
        binding.etUsername.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isDataChanged = true
                showSaveButton()
            }
        }
        binding.birthday.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                isDataChanged = true
                showSaveButton()
            }
        }
    }

    private fun setData() {
        binding.name.setText(Utils.name)
        binding.surname.setText(Utils.surname)
        binding.etUsername.setText(Utils.username)
        binding.birthday.setText(Utils.birthday)
        binding.etMail.setText(Utils.email)
        binding.phoneNumber.text = Utils.phoneNumber
        binding.userPhoto.setImageURI(Utils.selectedImageUri)
    }

    private fun saveData() {
        Utils.name = binding.name.text.toString()
        Utils.surname = binding.surname.text.toString()
        Utils.username = binding.etUsername.text.toString()
        Utils.birthday = binding.birthday.text.toString()
        Utils.email = binding.etMail.text.toString()
        Utils.phoneNumber = binding.phoneNumber.text.toString()
        Utils.selectedImageUri = selectedImageUri
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
            Glide.with(this).load(selectedImageUri).into(binding.userPhoto)
        }
    }
    private fun navigateToProfileFragment() {
        findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
    }

    private fun navigateToPhoneNumberConfirmation() {
        findNavController().navigate(R.id.action_editProfileFragment_to_addNumberFragment)
    }

    private fun showSaveButton() {
        binding.ready.visibility = View.VISIBLE
    }
}