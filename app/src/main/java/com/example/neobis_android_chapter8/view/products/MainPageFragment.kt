package com.example.neobis_android_chapter8.view.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.adapter.ProductListAdapter
import com.example.neobis_android_chapter8.databinding.FragmentMainPageBinding
import com.example.neobis_android_chapter8.model.ProductModel.Product
import com.example.neobis_android_chapter8.viewModels.ProductViewModel.MainPageViewModel

class MainPageFragment : Fragment() {
    private lateinit var binding: FragmentMainPageBinding
    private lateinit var adapter: ProductListAdapter
    private val mainPageViewModel: MainPageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).showBtmNav()
        setupRV()
        mainPageViewModel.fetchProductList(
            onSuccess = {adapter.updateProduct(it)},
            onError = {Toast.makeText(requireContext(), "Не удалось загрузить товары", Toast.LENGTH_SHORT).show() }
        )
    }

    private fun setupRV() {
        adapter = ProductListAdapter()
        val recyclerView = binding.rvProducts
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
        })
    }
}