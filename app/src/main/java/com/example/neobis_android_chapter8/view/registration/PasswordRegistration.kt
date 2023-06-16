package com.example.neobis_android_chapter8.view.registration

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.Utils
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentPasswordRegistrationBinding
import com.example.neobis_android_chapter8.model.Register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PasswordRegistration : Fragment() {
    private lateinit var binding: FragmentPasswordRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPasswordRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        setupClickListeners()
        setupPasswordValidation()
    }

    private fun setupNavigation() {
        binding.buttonNext.setOnClickListener {
            register()
        }
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_passwordRegistration_to_registration)
        }
    }
    private fun setupClickListeners() {
        binding.hidePassword.setOnClickListener {
            togglePasswordVisibilityCreate()
        }
    }
    private fun setupPasswordValidation() {
        binding.createPasswordEt.addTextChangedListener {
            val password = it.toString()
            var isPasswordMatch = false

            val hasUpperCase = password.matches(".*[A-Z].*".toRegex())
            val hasNumber = password.matches(".*\\d.*".toRegex())
            val hasSpecialSymbol = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*".toRegex())
            if (password == binding.repeatPasswordEt.text.toString()) {
                isPasswordMatch = true
            }

            val isValid = hasUpperCase && hasNumber && hasSpecialSymbol && isPasswordMatch
            if (isValid) {
                binding.buttonNext.isEnabled = true
                binding.buttonNext.setBackgroundResource(R.color.btn_valid)
            }
        }

        binding.createPasswordEt.addTextChangedListener {
            val createPassword = it.toString()
            binding.repeatPasswordEt.isVisible = createPassword.isNotEmpty()
        }
    }
    private fun togglePasswordVisibilityCreate() {
        val isPasswordVisible = binding.createPasswordEt.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod = if (isPasswordVisible) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.createPasswordEt.transformationMethod = newTransformationMethod
        binding.createPasswordEt.setSelection(binding.createPasswordEt.text?.length ?: 0)

        val isPasswordVisible2 = binding.repeatPasswordEt.transformationMethod == HideReturnsTransformationMethod.getInstance()
        val newTransformationMethod2 = if (isPasswordVisible2) PasswordTransformationMethod.getInstance() else HideReturnsTransformationMethod.getInstance()
        binding.repeatPasswordEt.transformationMethod = newTransformationMethod2
        binding.repeatPasswordEt.setSelection(binding.repeatPasswordEt.text?.length ?: 0)
    }

    private fun register() {
        val username = Utils.username
        val email = Utils.email
        val password = binding.createPasswordEt.text?.toString() ?: ""
        val passwordRepeat = binding.repeatPasswordEt.text?.toString() ?: ""
        val request = Register(username, email, password, passwordRepeat)
        val apiInterface = RetrofitInstance.api

        val call = apiInterface.register(request)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_passwordRegistration_to_profileFragment)
                    Toast.makeText(requireContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
            }
        })
    }
}