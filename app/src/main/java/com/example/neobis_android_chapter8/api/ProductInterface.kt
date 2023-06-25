package com.example.neobis_android_chapter8.api

import com.example.neobis_android_chapter8.model.*
import com.example.neobis_android_chapter8.model.AuthModel.*
import com.example.neobis_android_chapter8.model.ProductModel.CreateProductRequest
import com.example.neobis_android_chapter8.model.ProductModel.CreateProductResponse
import com.example.neobis_android_chapter8.model.ProductModel.Product
import retrofit2.Call
import retrofit2.http.*

interface ProductInterface {
    @GET("product/")
    fun productList(): Call<List<Product>>

    @POST("product/")
    fun productCreate(@Body request: CreateProductRequest): Call<CreateProductResponse>

    @GET("product/my/")
    fun productMyList(): Call<List<Product>>

    @GET("product/{id}/")
    fun productRead(@Path("id") id: Int): Call<Product>

    @PUT("product/{id}/")
    fun productUpdate(
        @Path("id") id: Int,
        @Body data: Product
    ): Call<Product>

    @PATCH("product/{id}/")
    fun productPartialUpdate(
        @Path("id") id: Int,
        @Body data: Product
    ): Call<Product>

    @DELETE("product/{id}/")
    fun productDelete(@Path("id") id: Int): Call<Unit>
}