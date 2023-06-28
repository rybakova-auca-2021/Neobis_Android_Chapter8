package com.example.neobis_android_chapter8.viewModels.ProductViewModel

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.adapter.RecyclerViewAdapter
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.ProductModel.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPageViewModel : ViewModel() {
     fun fetchProductList(onSuccess: (List<Product>) -> Unit, onError: () -> Unit) {
        val apiInterface = RetrofitInstance.productApi

        val call = apiInterface.productList()
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