package com.example.neobis_android_chapter8.view.registration

import com.example.neobis_android_chapter8.viewModels.AuthViewModel.EnterCodeViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentEnterCodeBinding

class EnterCodeFragment : Fragment() {
    private lateinit var binding: FragmentEnterCodeBinding
    val enterCodeViewModel: EnterCodeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).show()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_enterCodeFragment_to_addNumberFragment)
        }
        binding.buttonNext.setOnClickListener {
            val code = binding.etPhone.text.toString()
            enterCodeViewModel.enterCode(this, code)
        }
    }
}