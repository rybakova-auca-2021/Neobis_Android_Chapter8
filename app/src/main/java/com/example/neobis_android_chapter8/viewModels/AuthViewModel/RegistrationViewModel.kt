package com.example.neobis_android_chapter8.viewModels.AuthViewModel

import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.AuthModel.Register
import com.example.neobis_android_chapter8.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel : ViewModel() {
    fun register(
        password: String,
        password_repeat: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val username = Utils.username
        val email = Utils.email
        val request = Register(username, email, password, password_repeat)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.register(request)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onError.invoke()
            }
        })
    }
}