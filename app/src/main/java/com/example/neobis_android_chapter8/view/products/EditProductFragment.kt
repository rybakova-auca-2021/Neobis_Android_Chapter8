package com.example.neobis_android_chapter8.view.products

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentEditProductBinding
import com.example.neobis_android_chapter8.model.ProductModel.Product
import com.example.neobis_android_chapter8.utils.ImageHelper
import com.example.neobis_android_chapter8.viewModels.ProductViewModel.ReadProductViewModel

class EditProductFragment : Fragment() {
    private lateinit var binding: FragmentEditProductBinding

    private val editViewModel: EditProductViewModel by viewModels()
    private val infoViewModel: ReadProductViewModel by viewModels()

    private lateinit var addButton: ImageView
    private lateinit var imageContainer: ViewGroup
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupPhoto()
        getProductFromArguments()
        getInfo()
    }

    private fun setupNavigation() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnReady.setOnClickListener {
            updateProduct()
        }
        binding.addProductImg.setOnClickListener {
            ImageHelper.openGallery(this)
        }
    }

    private fun getProductFromArguments() {
        arguments?.let {
            product = it.getParcelable("products") ?: throw IllegalArgumentException("Product argument not found")
        }
    }

    private fun setupPhoto() {
        addButton = binding.addProductImg
        imageContainer = binding.imageContainer
    }

    private fun updateProduct() {
        val title = binding.inputName.text.toString()
        val price = binding.inputPrice.text.toString()
        val shortDesc = binding.inputShortDesc.text.toString()
        val fullDesc = binding.inputFullDesc.text.toString()

        editViewModel.updateProduct(
            product.id,
            requireContext(),
            title = title,
            price = price,
            shortDesc = shortDesc,
            fullDesc = fullDesc,
            onSuccess = {
                Toast.makeText(requireContext(),getString(R.string.changesSaved),Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.myProductsFragment)
            },
            onError = {
                Toast.makeText(requireContext(),getString(R.string.tryAgain),Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun getInfo() {
        infoViewModel.getProductData(product.id)
        infoViewModel.productData.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.inputName.text = Editable.Factory.getInstance().newEditable(it.title)
                binding.inputPrice.text = Editable.Factory.getInstance().newEditable(it.price)
                binding.inputShortDesc.text = Editable.Factory.getInstance().newEditable(it.short_description)
                binding.inputFullDesc.text = Editable.Factory.getInstance().newEditable(it.full_description)

                val images = product.images
                binding.imageContainer.removeAllViews()

                val addProductImageView = binding.addProductImg

                images.forEach { image ->
                    val imageView = ImageView(requireContext())
                    val layoutParams = LinearLayout.LayoutParams(
                        addProductImageView.width,
                        addProductImageView.height
                    )
                    layoutParams.setMargins(6, 0, 6, 0) // Set margin of 6dp
                    imageView.layoutParams = layoutParams
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    Glide.with(this)
                        .load(image)
                        .into(imageView)
                    imageView.clipToOutline = true
                    binding.imageContainer.addView(imageView)
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ImageHelper.handleImageSelection(this, requestCode, resultCode, data, addButton, imageContainer)
    }
}