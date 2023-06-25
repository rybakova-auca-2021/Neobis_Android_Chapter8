package com.example.neobis_android_chapter8.model.ProductModel

import okhttp3.MultipartBody

data class CreateProductRequest(
    val images: List<MultipartBody.Part>,
    val title: String,
    val price: String,
    val shortDescription: String,
    val fullDescription: String
)
