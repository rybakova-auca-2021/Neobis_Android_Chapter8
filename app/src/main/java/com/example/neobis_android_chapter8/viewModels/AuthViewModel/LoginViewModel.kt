package com.example.neobis_android_chapter8.viewModels.AuthViewModel

import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.AuthModel.Login
import com.example.neobis_android_chapter8.model.AuthModel.LoginResponse
import com.example.neobis_android_chapter8.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val request = Login(username, password)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.login(request)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val accessToken = loginResponse?.access
                    val refreshToken = loginResponse?.refresh
                    if (refreshToken != null && accessToken != null) {
                        Utils.access_token = accessToken
                    }
                    onSuccess.invoke()
                } else {
                    onError.invoke("Пользователь не найден, попробуйте ввести данные еще раз")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onError.invoke("Повторите попытку")
            }
        })
    }
}