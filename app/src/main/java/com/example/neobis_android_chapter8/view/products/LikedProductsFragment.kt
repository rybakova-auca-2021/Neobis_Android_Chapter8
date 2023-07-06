package com.example.neobis_android_chapter8.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.adapter.ProductListAdapter
import com.example.neobis_android_chapter8.databinding.FragmentLikedProductsBinding
import com.example.neobis_android_chapter8.model.ProductModel.Product
import com.example.neobis_android_chapter8.viewModels.ProductViewModel.LikeProductViewModel

class LikedProductsFragment : Fragment() {
    private lateinit var binding: FragmentLikedProductsBinding
    private val likeProductViewModel: LikeProductViewModel by viewModels()
    private lateinit var adapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLikedProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ProductListAdapter()
        setupRV()
        setupNavigation()
        getList()
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    private fun setupRV() {
        val recyclerView = binding.rvLikedProducts
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter.setOnItemClick(object : ProductListAdapter.ListClickListener<Product> {
            override fun onClick(data: Product, position: Int) {
                val fragment = ProductDetailFragment().apply {
                    arguments = Bundle().apply { putParcelable("products", data) }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.hostFragment, fragment)
                    .addToBackStack(null)
                    .commit()
            }

            override fun onThreeDotsClick(data: Product, position: Int) {
            }

            override fun onLikeClick(data: Product, position: Int) {}
        })
    }

    private fun getList() {
        likeProductViewModel.getFavoriteProducts(
            onSuccess = { productList ->
                adapter.updateProduct(productList)
                binding.boxImg.isVisible = productList.isEmpty()
                binding.msg.isVisible = productList.isEmpty()
            },
            onError = {
                Toast.makeText(requireContext(), getString(R.string.auth_error), Toast.LENGTH_SHORT).show()
            }
        )
    }
}