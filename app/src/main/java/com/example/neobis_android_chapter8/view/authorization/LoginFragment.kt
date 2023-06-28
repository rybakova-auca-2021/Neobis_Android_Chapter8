package com.example.neobis_android_chapter8.view.authorization

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentLoginBinding
import com.example.neobis_android_chapter8.utils.Utils
import com.example.neobis_android_chapter8.viewModels.AuthViewModel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private var isPasswordVisible = false
    private val viewModel: LoginViewModel by viewModels()

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
            val password = binding.password.text.toString()

            viewModel.login(username, password,
                onSuccess = {
                    findNavController().navigate(R.id.action_login_to_profileFragment)
                    Utils.username = username
                },
                onError = { errorMessage ->
                    showErrorDialog()
                    clearFields()
                }
            )
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

    private fun showErrorDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.error_message, null)

        val dialog = AlertDialog.Builder(requireContext(),  R.style.LightDialogTheme).setView(dialogView).create()
        dialog.setCancelable(true)
        dialog.show()
    }

    private fun clearFields() {
        binding.username.text = null
        binding.password.text = null
    }
}