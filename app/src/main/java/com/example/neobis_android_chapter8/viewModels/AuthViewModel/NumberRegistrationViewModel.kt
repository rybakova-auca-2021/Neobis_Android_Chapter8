package com.example.neobis_android_chapter8.viewModels.AuthViewModel

import android.content.Context
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentAddNumberBinding
import com.example.neobis_android_chapter8.model.AuthModel.RegisterResponseModel
import com.example.neobis_android_chapter8.utils.LocalStorageProvider
import com.example.neobis_android_chapter8.utils.Utils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class NumberRegistrationViewModel : ViewModel() {

    fun fullRegister(
        context: Context,
        firstName: String,
        lastName: String,
        birthday: String,
        phoneNumber: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val photo = Utils.selectedImageUri
        val apiInterface = RetrofitInstance.authApi

        val file: File? = photo?.let { LocalStorageProvider.getFile(context, it) }
        val requestBody = file?.asRequestBody("photo/*".toMediaTypeOrNull())
        val imagePart = requestBody?.let {
            MultipartBody.Part.createFormData("photo", file.name, it)
        }

        val firstNamePart = firstName.toRequestBody("text/plain".toMediaTypeOrNull())
        val lastNamePart = lastName.toRequestBody("text/plain".toMediaTypeOrNull())
        val birthdayPart = birthday.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumberPart = phoneNumber.toRequestBody("text/plain".toMediaTypeOrNull())

        imagePart?.let {
            apiInterface.registerUser(
                firstNamePart,
                lastNamePart,
                birthdayPart,
                phoneNumberPart,
                it
            )
        }?.enqueue(object : Callback<RegisterResponseModel> {
            override fun onResponse(
                call: Call<RegisterResponseModel>,
                response: Response<RegisterResponseModel>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke()
                }
            }

            override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                onError.invoke()
            }
        })
    }
}