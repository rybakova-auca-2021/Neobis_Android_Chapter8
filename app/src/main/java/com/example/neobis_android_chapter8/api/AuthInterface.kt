package com.example.neobis_android_chapter8.api

import com.example.neobis_android_chapter8.model.*
import com.example.neobis_android_chapter8.model.AuthModel.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthInterface {
    @POST("account/register/")
    fun register(@Body request: Register): Call<Unit>

    @POST("account/confirm/")
    fun confirm(@Body request: Confirm): Call<Unit>

    @Multipart
    @POST("account/full_register/")
    fun registerUser(@Part("first_name") firstName: RequestBody,
                     @Part("last_name") lastName: RequestBody,
                     @Part("birthday") birthday: RequestBody,
                     @Part("phone_number") phoneNumber: RequestBody,
                     @Part photo: MultipartBody.Part): Call<RegisterResponseModel>

    @POST("account/login/")
    fun login(@Body request: Login): Call<LoginResponse>

    @GET("account/profile/")
    fun getProfile(): Call<Profile>
}