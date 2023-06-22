import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentLoginBinding
import com.example.neobis_android_chapter8.model.Login
import com.example.neobis_android_chapter8.model.LoginResponse
import com.example.neobis_android_chapter8.utils.Utils
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private lateinit var binding: FragmentLoginBinding

    fun login(fragment: Fragment, username: String, password: String) {
        val request = Login(username, password)
        val apiInterface = RetrofitInstance.api

        val call = apiInterface.login(request)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val accessToken = loginResponse?.access
                    val refreshToken = loginResponse?.refresh
                    if (refreshToken != null && accessToken != null) {
                        Utils.access_token = accessToken
                    }
                    fragment.findNavController().navigate(R.id.action_login_to_profileFragment)
                } else {
                    showSnackbar("Пользователь не найден, попробуйте ввести данные еще раз")
                    clearFields()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showSnackbar("Повторите попытку")
            }
        })
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun clearFields() {
        binding.username.text = null
        binding.password.text = null
    }
}