package com.example.neobis_android_chapter8.viewModels.AuthViewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentLoginBinding
import com.example.neobis_android_chapter8.model.AuthModel.Login
import com.example.neobis_android_chapter8.model.AuthModel.LoginResponse
import com.example.neobis_android_chapter8.utils.Utils
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    fun login(fragment: Fragment, binding: FragmentLoginBinding, username: String, password: String) {
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
                    fragment.findNavController().navigate(R.id.action_login_to_profileFragment)
                } else {
                    showSnackbar(binding,"Пользователь не найден, попробуйте ввести данные еще раз")
                    clearFields(binding)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showSnackbar(binding,"Повторите попытку")
            }
        })
    }

    private fun showSnackbar(binding: FragmentLoginBinding, message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun clearFields(binding: FragmentLoginBinding) {
        binding.username.text = null
        binding.password.text = null
    }
}