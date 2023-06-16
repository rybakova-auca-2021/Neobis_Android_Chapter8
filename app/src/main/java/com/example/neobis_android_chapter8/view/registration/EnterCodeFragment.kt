package com.example.neobis_android_chapter8.view.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentEnterCodeBinding

class EnterCodeFragment : Fragment() {
    private lateinit var binding: FragmentEnterCodeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =  FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }
}