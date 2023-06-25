package com.example.neobis_android_chapter8.view.products

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentAddProductBinding
import com.example.neobis_android_chapter8.viewModels.ProductViewModel.AddProductViewModel

class AddProductFragment : Fragment() {
    private lateinit var binding: FragmentAddProductBinding
    private val PICK_IMAGE_REQUEST = 1

    private val addProductViewModel: AddProductViewModel by viewModels()
    private val imageList: MutableList<String> = mutableListOf()

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
        callGallery()
        setupNavigation()
    }
    private fun setupNavigation() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_addProductFragment_to_mainPageFragment)
        }
        binding.btnReady.setOnClickListener {
            val title = binding.inputName.text.toString()
            val price = binding.inputPrice.text.toString()
            val shortDesc = binding.inputShortDesc.text.toString()
            val fullDesc = binding.inputFullDesc.text.toString()

            addProductViewModel.createProduct(this, binding, imageList, title, price, shortDesc, fullDesc)
        }
        binding.addProductImg.setOnClickListener {
            openGallery()
        }
    }

    private fun callGallery() {
        addButton = binding.addProductImg
        imageContainer = binding.imageContainer

        addButton.setOnClickListener {
            openGallery()
        }
    }

    @SuppressLint("IntentReset")
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            val imageView = ImageView(requireContext())
            val layoutParams = LinearLayout.LayoutParams(
                addButton.width,
                addButton.height
            )
            layoutParams.setMargins(6, 0, 6, 0) // Set margin of 6dp
            imageView.layoutParams = layoutParams
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setImageURI(imageUri)
            imageView.clipToOutline = true
            imageContainer.addView(imageView)

            imageUri?.let {
                val imagePath = getImagePath(imageUri)
                imagePath?.let {
                    imageList.add(it)
                }
            }
        }
    }

    private fun getImagePath(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val imagePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()
        return imagePath
    }
}
