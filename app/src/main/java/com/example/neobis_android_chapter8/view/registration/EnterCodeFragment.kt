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
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentEnterCodeBinding
import com.example.neobis_android_chapter8.model.Confirm
import com.example.neobis_android_chapter8.model.FullRegister
import com.example.neobis_android_chapter8.model.RegisterResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterCodeFragment : Fragment() {
    private lateinit var binding: FragmentEnterCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =  FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
    }

    fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_enterCodeFragment_to_addNumberFragment)
        }
        binding.buttonNext.setOnClickListener {
            val code = binding.etPhone.text.toString()
            codeConfirmation(code)
        }
    }

    private fun codeConfirmation(code: String) {
        val request = Confirm(code)
        val apiInterface = RetrofitInstance.api

        val call = apiInterface.confirm(request)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Подтверждение прошло успешно", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_enterCodeFragment_to_profileFragment)
                } else {
                    binding.errorMsg.isVisible = true
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
            }
        })
    }
}