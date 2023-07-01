package com.example.neobis_android_chapter8.utils
import android.net.Uri

object Utils {
    var username = ""
    var email = ""
    var name = ""
    var surname = ""
    var birthday = ""
    var phoneNumber = ""
    var selectedImageUri: Uri? = null
    var access_token = ""

    val imageList: MutableList<Uri> = mutableListOf()

    fun addImageUri(uri: Uri) {
        imageList.add(uri)
    }
}