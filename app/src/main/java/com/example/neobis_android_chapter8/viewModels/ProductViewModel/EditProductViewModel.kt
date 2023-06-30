package com.example.neobis_android_chapter8.view.products

import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.ProductModel.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProductViewModel : ViewModel() {

    fun updateProduct(
        id: Int,
        product: Product,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val apiInterface = RetrofitInstance.productApi

        val call = apiInterface.productUpdate(id, product)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                onError.invoke()
            }
        })
    }
}