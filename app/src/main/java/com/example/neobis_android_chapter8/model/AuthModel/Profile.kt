package com.example.neobis_android_chapter8.model.AuthModel

data class Profile(
    val username: String,
    val email: String,
    val first_name: String,
    var last_name: String,
    var birthday: String,
    var phone_number: String,
    var photo: String
)
