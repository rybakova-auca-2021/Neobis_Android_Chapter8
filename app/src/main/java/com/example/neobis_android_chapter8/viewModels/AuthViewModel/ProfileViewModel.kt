package com.example.neobis_android_chapter8.viewModels.AuthViewModel

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.model.AuthModel.Profile
import com.example.neobis_android_chapter8.utils.ProfileInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    fun getInfo(fragment: Fragment) {
        val apiInterface = RetrofitInstance.authApi
        val call = apiInterface.getProfile()

        call.enqueue(object : Callback<Profile> {
            override fun onResponse(
                call: Call<Profile>,
                response: Response<Profile>
            ) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    val username = profile?.username
                    val name = profile?.first_name
                    val email = profile?.email
                    val surname = profile?.last_name
                    val phoneNumber = profile?.phone_number
                    val birthday = profile?.birthday
                    if (username != null) {
                        ProfileInfo.username = username
                    }
                    if (name != null) {
                        ProfileInfo.name = name
                    }
                    if (surname != null) {
                        ProfileInfo.surname = surname
                    }
                    if (email != null) {
                        ProfileInfo.email = email
                    }
                    if (phoneNumber != null) {
                        ProfileInfo.phoneNumber = phoneNumber
                    }
                    if (birthday != null) {
                        ProfileInfo.birthday = birthday
                    }
                } else {
                    Toast.makeText(fragment.requireContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Profile>, t: Throwable) {
                Toast.makeText(fragment.requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
            }
        })
    }
}