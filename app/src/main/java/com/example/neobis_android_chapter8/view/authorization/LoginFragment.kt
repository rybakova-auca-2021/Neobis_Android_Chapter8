package com.example.neobis_android_chapter8.view.authorization

import LoginViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.utils.Utils
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentLoginBinding
import com.example.neobis_android_chapter8.model.Login
import com.example.neobis_android_chapter8.model.LoginResponse
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var isPasswordVisible = false
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()
        changeButtonColor()
        setupNavigation()
        setupPasswordVisibilityToggle()
    }

    private fun setupNavigation() {
        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registration)
        }
        binding.buttonNext.setOnClickListener {
            val username = binding.username.text.toString()
            Utils.username = username
            val password = binding.password.text.toString()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(this, username, password)
            }
        }
    }
    private fun setupPasswordVisibilityToggle() {
        binding.hidePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            val transformationMethod = if (isPasswordVisible)
                HideReturnsTransformationMethod.getInstance()
            else
                PasswordTransformationMethod.getInstance()

            binding.password.transformationMethod = transformationMethod
            binding.hidePassword.setImageResource(
                if (isPasswordVisible)
                    R.drawable.show_password
                else
                    R.drawable.hide_password
            )
        }
    }
    @SuppressLint("ResourceAsColor")
    private fun changeButtonColor() {
        val username = binding.username.text.toString()
        val password = binding.password.text.toString()
        val validColor = ContextCompat.getColor(requireContext(), R.color.btn_valid)

        if (password.isNotEmpty() && username.isNotEmpty()) {
            binding.buttonNext.setBackgroundColor(validColor)
            binding.buttonNext.isClickable = true
        }
    }
}