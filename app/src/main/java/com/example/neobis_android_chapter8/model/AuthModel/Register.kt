package com.example.neobis_android_chapter8.model.AuthModel

data class Register(
    val username: String,
    val email: String,
    val password: String,
    val password_repeat: String
)
