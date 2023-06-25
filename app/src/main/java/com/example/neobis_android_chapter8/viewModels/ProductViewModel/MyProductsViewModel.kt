package com.example.neobis_android_chapter8.viewModels.ProductViewModel

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.adapter.RecyclerViewAdapter
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentMyProductsBinding
import com.example.neobis_android_chapter8.model.ProductModel.Product
import com.example.neobis_android_chapter8.utils.ProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProductsViewModel : ViewModel() {
    fun fetchProductList(binding: FragmentMyProductsBinding, adapter: RecyclerViewAdapter, fragment: Fragment) {
        binding.progressBar.visibility = View.VISIBLE

        Handler().postDelayed({
            val apiInterface = RetrofitInstance.productApi

            val call = apiInterface.productMyList()
            call.enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    binding.progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        val productList = response.body()
                        productList?.let {
                            adapter.updateProduct(it)
                        }
                        binding.boxImg.isVisible = false
                        binding.msg.isVisible = false
                    } else {
                        Toast.makeText(fragment.requireContext(), "Не удалось загрузить товары", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(fragment.requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
                }
            })
        }, 1000)
    }

    fun deleteProduct(
        binding: FragmentMyProductsBinding,
        fragment: Fragment,
        position: Int,
        adapter: RecyclerViewAdapter
    ) {
        val apiInterface = RetrofitInstance.productApi
        val id = ProductData.id
        id?.let { apiInterface.productDelete(it) }?.enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful) {
                    adapter.removeItem(position)
                    addCardViewToContainer(fragment, binding)
                } else {
                    Toast.makeText(fragment.requireContext(), "Не удалось удалить товар", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(fragment.requireContext(), "Повторите попытку", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun addCardViewToContainer(fragment: Fragment, binding: FragmentMyProductsBinding) {
        val cardViewContent = LayoutInflater.from(fragment.requireContext()).inflate(R.layout.product_deleted_msg, null)
        val cardView = CardView(fragment.requireContext())
        cardView.addView(cardViewContent)

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        binding.container.addView(cardView, layoutParams)
    }
}