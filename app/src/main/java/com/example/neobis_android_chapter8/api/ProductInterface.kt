package com.example.neobis_android_chapter8.api

import com.example.neobis_android_chapter8.model.*
import com.example.neobis_android_chapter8.model.AuthModel.*
import com.example.neobis_android_chapter8.model.ProductModel.CreateProductResponse
import com.example.neobis_android_chapter8.model.ProductModel.Product
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ProductInterface {
    @GET("product/")
    fun productList(): Call<List<Product>>

    @Multipart
    @POST("product/")
    fun productCreate(@Part images: List<MultipartBody.Part>,
                      @Part("title") title: RequestBody,
                      @Part("price") price: RequestBody,
                      @Part("short_description") shortDesc: RequestBody,
                      @Part("full_description") fullDesc: RequestBody): Call<CreateProductResponse>

    @GET("product/my/")
    fun productMyList(): Call<List<Product>>

    @GET("product/{id}/")
    fun productRead(@Path("id") id: Int): Call<Product>

    @Multipart
    @PUT("product/{id}/")
    fun productUpdate(
        @Path("id") id: Int,
        @Part images: List<MultipartBody.Part>,
        @Part("title") title: RequestBody,
        @Part("price") price: RequestBody,
        @Part("short_description") shortDesc: RequestBody,
        @Part("full_description") fullDesc: RequestBody
    ): Call<Product>

    @DELETE("product/{id}/")
    fun productDelete(@Path("id") id: Int): Call<Unit>
}