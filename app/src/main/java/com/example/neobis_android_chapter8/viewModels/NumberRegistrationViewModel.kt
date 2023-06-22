package com.example.neobis_android_chapter8.viewModels

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentAddNumberBinding
import com.example.neobis_android_chapter8.model.FullRegister
import com.example.neobis_android_chapter8.model.RegisterResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NumberRegistrationViewModel : ViewModel() {
    private lateinit var binding: FragmentAddNumberBinding

    fun fullRegister(fragment: Fragment, last_name: String, birthday: String, phone_number: String) {
        val request = FullRegister(last_name, birthday, phone_number)
        val apiInterface = RetrofitInstance.api

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
                    clearFields()
                }
            }

            override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                Toast.makeText(fragment.requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun clearFields() {
        binding.etPhone.text = null
    }
}