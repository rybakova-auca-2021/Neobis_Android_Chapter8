package com.example.neobis_android_chapter8.api

import com.example.neobis_android_chapter8.model.Confirm
import com.example.neobis_android_chapter8.model.FullRegister
import com.example.neobis_android_chapter8.model.Login
import com.example.neobis_android_chapter8.model.Register

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("register/")
    fun register(@Body request: Register): Call<Unit>

    @POST("confirm/")
    fun confirm(@Body request: Confirm): Call<Unit>

    @POST("full_register/")
    fun registerUser(@Body request: FullRegister): Call<Unit>

    @POST("login/")
    fun login(@Body request: Login): Call<Unit>
}