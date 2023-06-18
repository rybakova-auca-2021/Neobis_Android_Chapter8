package com.example.neobis_android_chapter8.view.registration

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.Utils
import com.example.neobis_android_chapter8.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
       binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        changeButtonColor()
    }

    private fun setupNavigation() {
        binding.buttonNext.setOnClickListener {
            getData()
            findNavController().navigate(R.id.action_registration_to_passwordRegistration)
        }
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_registration_to_login)
        }
    }

    private fun getData() {
        val username = binding.username.text.toString()
        Utils.username = username

        val email = binding.email.text.toString()
        Utils.email = email
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun changeButtonColor() {
        binding.email.addTextChangedListener { text ->
            val email = text?.toString() ?: ""
            val isValidEmail = isValidEmail(email)
            val buttonColor = if (isValidEmail) R.color.btn_valid else R.color.btn_invalid
            binding.buttonNext.isEnabled = isValidEmail
            binding.buttonNext.setBackgroundColor(requireContext().getColor(buttonColor))
        }
    }
}