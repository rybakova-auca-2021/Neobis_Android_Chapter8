package com.example.neobis_android_chapter8.viewModels.AuthViewModel

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentAddNumberBinding
import com.example.neobis_android_chapter8.model.AuthModel.FullRegister
import com.example.neobis_android_chapter8.model.AuthModel.RegisterResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NumberRegistrationViewModel : ViewModel() {

    fun fullRegister(fragment: Fragment, binding: FragmentAddNumberBinding, first_name: String, last_name: String, birthday: String, phone_number: String) {
        val request = FullRegister(first_name, last_name, birthday, phone_number)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.registerUser(request)
        call.enqueue(object : Callback<RegisterResponseModel> {
            override fun onResponse(
                call: Call<RegisterResponseModel>,
                response: Response<RegisterResponseModel>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(fragment.requireContext(), "Код отправлен на ваш номер телефона", Toast.LENGTH_SHORT).show()
                    fragment.findNavController().navigate(R.id.action_addNumberFragment_to_enterCodeFragment)
                } else {
                    binding.errorMsg.isVisible = true
                    clearFields(binding)
                }
            }

            override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                Toast.makeText(fragment.requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun clearFields(binding: FragmentAddNumberBinding) {
        binding.etPhone.text = null
    }
}