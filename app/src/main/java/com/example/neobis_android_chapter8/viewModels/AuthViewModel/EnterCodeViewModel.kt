package com.example.neobis_android_chapter8.viewModels.AuthViewModel

import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.AuthModel.Confirm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterCodeViewModel : ViewModel() {

    fun enterCode(code: String, onSuccess: () -> Unit, onError: () -> Unit) {
        val request = Confirm(code)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.confirm(request)
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