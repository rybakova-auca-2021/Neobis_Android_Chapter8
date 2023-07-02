package com.example.neobis_android_chapter8.view.registration

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentPasswordRegistrationBinding
import com.example.neobis_android_chapter8.viewModels.AuthViewModel.RegistrationViewModel

class PasswordRegistration : Fragment() {
    private lateinit var binding: FragmentPasswordRegistrationBinding
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPasswordRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).showBtmNav()
        setupNavigation()
        setupClickListeners()
        setupPasswordValidation()
    }

    private fun setupNavigation() {
        binding.buttonNext.setOnClickListener {
            setupRegistration()
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

    private fun setupRegistration() {
        val password = binding.createPasswordEt.text?.toString() ?: ""
        val passwordRepeat = binding.repeatPasswordEt.text?.toString() ?: ""
        if (password.isNotEmpty() && passwordRepeat.isNotEmpty() && password == passwordRepeat) {
            viewModel.register(password, passwordRepeat,
                onSuccess = {
                    findNavController().navigate(R.id.action_passwordRegistration_to_profileFragment)
                    Toast.makeText(requireContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                },
                onError = {
                    Toast.makeText(requireContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun setupPasswordValidation() {
        binding.createPasswordEt.addTextChangedListener {
            val password = it.toString()
            val passwordRepeat = binding.repeatPasswordEt.text.toString()

            val hasUpperCase = password.matches(".*[A-Z].*".toRegex())
            val hasNumber = password.matches(".*\\d.*".toRegex())
            val hasSpecialSymbol = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*".toRegex())
            if (password == passwordRepeat) {
                val isValid = hasUpperCase && hasNumber && hasSpecialSymbol
                if (isValid) {
                    binding.buttonNext.isEnabled = true
                    binding.buttonNext.setBackgroundResource(R.color.btn_valid)
                }
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
}