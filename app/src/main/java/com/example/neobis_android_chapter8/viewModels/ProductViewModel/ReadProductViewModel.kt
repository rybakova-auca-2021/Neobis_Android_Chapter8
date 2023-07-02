package com.example.neobis_android_chapter8.viewModels.ProductViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.ProductModel.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadProductViewModel : ViewModel() {
    val productData: MutableLiveData<Product> = MutableLiveData()

    fun getProductData(
        id: Int,
    ) {
        val apiInterface = RetrofitInstance.productApi

        val call = apiInterface.productRead(id)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    productData.value = product!!
                    fetchProductImages(product.images)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
            }
        })
    }
    private fun fetchProductImages(imagesUrl: List<String>?) {
        if (imagesUrl != null) {
            productData.value?.images = imagesUrl
        }
    }
}