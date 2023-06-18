package com.example.neobis_android_chapter8.view.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.Utils
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentAddNumberBinding
import com.example.neobis_android_chapter8.model.FullRegister
import com.example.neobis_android_chapter8.model.RegisterResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNumberFragment : Fragment() {
    private lateinit var binding: FragmentAddNumberBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.buttonNext.setOnClickListener {
            val phoneNumber = binding.etPhone.text.toString()
            saveData(phoneNumber)
            fullRegister(Utils.surname, Utils.birthday, phoneNumber)
        }
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_addNumberFragment_to_editProfileFragment)
        }
    }

    private fun saveData(phoneNumber: String) {
        Utils.phoneNumber = phoneNumber
    }

    private fun fullRegister(lastName: String, birthday: String, phoneNumber: String) {
        val request = FullRegister(lastName, birthday, phoneNumber)
        val apiInterface = RetrofitInstance.api

        val call = apiInterface.registerUser(request)
        call.enqueue(object : Callback<RegisterResponseModel> {
            override fun onResponse(call: Call<RegisterResponseModel>, response: Response<RegisterResponseModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Код отправлен на ваш номер телефона", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addNumberFragment_to_enterCodeFragment)
                } else {
                    binding.errorMsg.isVisible = true
                    clearFields()
                }
            }

            override fun onFailure(call: Call<RegisterResponseModel>, t: Throwable) {
                Toast.makeText(requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun clearFields() {
        binding.etPhone.text = null
    }
}