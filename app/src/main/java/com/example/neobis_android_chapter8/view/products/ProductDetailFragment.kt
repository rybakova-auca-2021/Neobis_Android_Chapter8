package com.example.neobis_android_chapter8.view.products

import android.os.Bundle
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
import com.example.neobis_android_chapter8.adapter.ItemPagerAdapter
import com.example.neobis_android_chapter8.databinding.FragmentProductDetailBinding
import com.example.neobis_android_chapter8.model.ProductModel.Product
import com.example.neobis_android_chapter8.viewModels.ProductViewModel.ReadProductViewModel

class ProductDetailFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val viewModel: ReadProductViewModel by viewModels()
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        getProductFromArguments()
        setProductInfo()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnChange.setOnClickListener {
            val fragment = EditProductFragment().apply {
                arguments = Bundle().apply { putParcelable("products", product) }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.hostFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun getProductFromArguments() {
        arguments?.let {
            product = it.getParcelable("products") ?: throw IllegalArgumentException("Product argument not found")
        }
    }

    private fun setProductInfo() {
        val id = product.id
        viewModel.getProductData(id)
        viewModel.productData.observe(viewLifecycleOwner) { updatedProduct ->
            updatedProduct?.let {
                binding.title.setText(it.title)
                binding.price.setText(it.price)
                binding.shortDescription.setText(it.short_description)
                binding.description.setText(it.full_description)

                val images = updatedProduct.images
                val adapter = ItemPagerAdapter(images)
                binding.viewPager.adapter = adapter
            }
        }
    }
}