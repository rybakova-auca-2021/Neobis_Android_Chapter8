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
import com.example.neobis_android_chapter8.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProductsViewModel : ViewModel() {
    fun fetchProductList(onSuccess: (List<Product>) -> Unit, onError: () -> Unit) {
        Handler().postDelayed({
            val apiInterface = RetrofitInstance.productApi

            val call = apiInterface.productMyList()
            call.enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
                ) {
                    if (response.isSuccessful) {
                        val productList = response.body()
                        productList?.let {
                            if(it.isNotEmpty()) {
                                onSuccess.invoke(it)
                            } else {
                                onSuccess.invoke(emptyList())
                            }
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    onError.invoke()
                }
            })
        }, 1000)
    }

    fun deleteProduct(
        onSuccess: () -> Unit,
        onError: () -> Unit,
        productId: Int
    ) {
        val apiInterface = RetrofitInstance.productApi
        apiInterface.productDelete(productId).enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onError.invoke()
            }
        })
    }
}