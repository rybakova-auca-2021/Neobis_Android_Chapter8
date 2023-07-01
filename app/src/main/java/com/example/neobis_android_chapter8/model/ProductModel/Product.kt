package com.example.neobis_android_chapter8.model.ProductModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    val id: Int,
    val image: String,
    val product: String
) : Parcelable

@Parcelize
data class Product(
    val id: Int,
    val user: String,
    val images: List<String>,
    val title: String,
    val price: String,
    val short_description: String?,
    val full_description: String?
) : Parcelable