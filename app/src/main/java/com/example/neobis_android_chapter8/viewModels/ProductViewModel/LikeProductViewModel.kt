package com.example.neobis_android_chapter8.viewModels.ProductViewModel

import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.ProductModel.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeProductViewModel : ViewModel() {
    fun likeProduct(
        onSuccess: () -> Unit,
        onError: () -> Unit,
        productId: Int
    ) {
        val apiInterface = RetrofitInstance.productApi
        apiInterface.likeProduct(productId).enqueue(object : Callback<Unit> {
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

    fun getFavoriteProducts(onSuccess: (List<Product>) -> Unit, onError: () -> Unit) {
        val apiInterface = RetrofitInstance.productApi

        val call = apiInterface.getFavoriteItems()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productList = response.body()
                    productList?.let {
                        onSuccess.invoke(it)
                    }
                } else {
                    onError.invoke()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                onError.invoke()
            }
        })
    }

}