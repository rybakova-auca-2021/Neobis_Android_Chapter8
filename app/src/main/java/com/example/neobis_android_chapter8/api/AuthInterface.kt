package com.example.neobis_android_chapter8.api

import com.example.neobis_android_chapter8.model.*
import com.example.neobis_android_chapter8.model.AuthModel.*

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthInterface {
    @POST("account/register/")
    fun register(@Body request: Register): Call<Unit>

    @POST("account/confirm/")
    fun confirm(@Body request: Confirm): Call<Unit>

    @POST("account/full_register/")
    fun registerUser(@Body request: FullRegister): Call<RegisterResponseModel>
    @POST("account/login/")
    fun login(@Body request: Login): Call<LoginResponse>
    @GET("account/profile/")
    fun getProfile(): Call<Profile>
}