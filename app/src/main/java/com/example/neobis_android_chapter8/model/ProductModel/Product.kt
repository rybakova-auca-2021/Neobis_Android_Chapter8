package com.example.neobis_android_chapter8.model.ProductModel

data class Image(
    val id: Int,
    val image: String,
    val product: String
)

data class Product(
    val id: Int,
    val user: String,
    val images: List<Image>,
    val title: String,
    val price: String,
    val shortDescription: String?,
    val fullDescription: String?
)