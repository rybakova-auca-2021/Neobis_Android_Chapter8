package com.example.neobis_android_chapter8.view.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentAddNumberBinding
import com.example.neobis_android_chapter8.utils.Utils
import com.example.neobis_android_chapter8.view.PhoneNumberMaskWatcher
import com.example.neobis_android_chapter8.viewModels.AuthViewModel.NumberRegistrationViewModel

class AddNumberFragment : Fragment() {
    private lateinit var binding: FragmentAddNumberBinding
    private val fullRegisterViewModel: NumberRegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()
        setupNavigation()
        setupMask()
    }

    private fun setupMask() {
        val phoneInput: EditText = binding.etPhone
        PhoneNumberMaskWatcher(phoneInput)
    }

    private fun setupNavigation() {
        binding.buttonNext.setOnClickListener {
            val phoneNumber = binding.etPhone.text.toString()
            saveData(phoneNumber)
            fullRegisterViewModel.fullRegister(this, Utils.surname, Utils.birthday, phoneNumber)
        }
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_addNumberFragment_to_editProfileFragment)
        }
    }

    private fun saveData(phoneNumber: String) {
        Utils.phoneNumber = phoneNumber
    }
}