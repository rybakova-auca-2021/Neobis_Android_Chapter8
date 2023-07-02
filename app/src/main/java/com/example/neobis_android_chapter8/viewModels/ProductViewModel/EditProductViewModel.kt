package com.example.neobis_android_chapter8.view.products

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.ProductModel.Product
import com.example.neobis_android_chapter8.utils.LocalStorageProvider
import com.example.neobis_android_chapter8.utils.Utils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditProductViewModel : ViewModel() {

    fun updateProduct(
        id: Int,
        fragment: Fragment,
        title: String,
        price: String,
        shortDesc: String,
        fullDesc: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val apiInterface = RetrofitInstance.productApi
        val images = Utils.imageList

        val imageParts = mutableListOf<MultipartBody.Part>()

        images.forEachIndexed { index, image ->
            val file: File? = LocalStorageProvider.getFile(fragment.requireContext(), image)
            val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = requestBody?.let {
                MultipartBody.Part.createFormData("images", file.name, it)
            }
            imagePart?.let { imageParts.add(it) }
        }

        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val pricePart = price.toRequestBody("text/plain".toMediaTypeOrNull())
        val shortDescPart = shortDesc.toRequestBody("text/plain".toMediaTypeOrNull())
        val fullDescPart = fullDesc.toRequestBody("text/plain".toMediaTypeOrNull())

        apiInterface.productUpdate(
            id,
            imageParts,
            title = titlePart,
            price = pricePart,
            shortDesc = shortDescPart,
            fullDesc = fullDescPart
        ).enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                onError()
            }
        })
    }
}