package com.example.neobis_android_chapter8.viewModels.ProductViewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentAddProductBinding
import com.example.neobis_android_chapter8.model.ProductModel.CreateProductRequest
import com.example.neobis_android_chapter8.model.ProductModel.CreateProductResponse
import com.example.neobis_android_chapter8.model.ProductModel.Image
import com.example.neobis_android_chapter8.utils.ProductData
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddProductViewModel : ViewModel() {
    fun createProduct(
        fragment: Fragment,
        binding: FragmentAddProductBinding,
        images: MutableList<String>,
        title: String,
        price: String,
        shortDesc: String,
        fullDesc: String
    ) {
        val imageList = mutableListOf<MultipartBody.Part>()

        for (imageUrl in images) {
            val imageFile = File(imageUrl)
            val imageRequestBody = imageFile.asRequestBody("image/*".toMediaType())
            val imagePart = MultipartBody.Part.createFormData("images", imageFile.name, imageRequestBody)
            imageList.add(imagePart)
        }

        val request = CreateProductRequest(imageList, title, price, shortDesc, fullDesc)
        val apiInterface = RetrofitInstance.productApi

        val call = apiInterface.productCreate(request)
        call.enqueue(object : Callback<CreateProductResponse> {
            override fun onResponse(call: Call<CreateProductResponse>, response: Response<CreateProductResponse>) {
                if (response.isSuccessful) {
                    val productId = response.body()?.id
                    ProductData.id = productId
                    fragment.findNavController().navigate(R.id.mainPageFragment)
                } else {
                    showSnackbar(binding, "Произошла ошибка. Попробуйте еще раз")
                }
            }

            override fun onFailure(call: Call<CreateProductResponse>, t: Throwable) {
                showSnackbar(binding, "Попробуйте еще раз")
            }
        })
    }

    private fun showSnackbar(binding: FragmentAddProductBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}
