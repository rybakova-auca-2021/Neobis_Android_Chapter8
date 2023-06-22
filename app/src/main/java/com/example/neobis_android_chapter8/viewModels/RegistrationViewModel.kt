import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentLoginBinding
import com.example.neobis_android_chapter8.model.Register
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel : ViewModel() {
    private lateinit var binding: FragmentLoginBinding

    fun register(fragment: Fragment, username: String, email: String, password: String, password_repeat: String) {
        val request = Register(username, email, password, password_repeat)
        val apiInterface = RetrofitInstance.api

        val call = apiInterface.register(request)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(
                call: Call<Unit>,
                response: Response<Unit>
            ) {
                if (response.isSuccessful) {
                    fragment.findNavController().navigate(R.id.action_passwordRegistration_to_profileFragment)
                    Toast.makeText(fragment.requireContext(), "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(fragment.requireContext(), "Попробуйте еще раз", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(fragment.requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}