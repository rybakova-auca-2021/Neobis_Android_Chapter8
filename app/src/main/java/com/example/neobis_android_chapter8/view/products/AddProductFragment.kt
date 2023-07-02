package com.example.neobis_android_chapter8.view.products

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentAddProductBinding
import com.example.neobis_android_chapter8.utils.ImageHelper
import com.example.neobis_android_chapter8.viewModels.ProductViewModel.AddProductViewModel
import com.google.android.material.snackbar.Snackbar

class AddProductFragment : Fragment() {
    private lateinit var binding: FragmentAddProductBinding

    private val addProductViewModel: AddProductViewModel by viewModels()

    private lateinit var addButton: ImageView
    private lateinit var imageContainer: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupPhoto()
    }

    private fun setupNavigation() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addProductFragment_to_mainPageFragment)
        }
        binding.btnReady.setOnClickListener {
            createProduct()
        }
        binding.addProductImg.setOnClickListener {
            ImageHelper.openGallery(this)
        }
    }

    private fun setupPhoto() {
        addButton = binding.addProductImg
        imageContainer = binding.imageContainer
    }

    private fun createProduct() {
        val title = binding.inputName.text.toString()
        val price = binding.inputPrice.text.toString()
        val shortDesc = binding.inputShortDesc.text.toString()
        val fullDesc = binding.inputFullDesc.text.toString()

        addProductViewModel.createProduct(
            requireContext(),
            title = title,
            price = price,
            shortDesc = shortDesc,
            fullDesc = fullDesc,
            onSuccess = { findNavController().navigate(R.id.mainPageFragment) },
            onError = { showSnackbar(binding, getString(R.string.error_msg)) }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ImageHelper.handleImageSelection(this, requestCode, resultCode, data, addButton, imageContainer)
    }

    private fun showSnackbar(binding: FragmentAddProductBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}
