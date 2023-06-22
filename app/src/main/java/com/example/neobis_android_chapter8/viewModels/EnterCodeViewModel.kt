import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.api.RetrofitInstance
import com.example.neobis_android_chapter8.databinding.FragmentEnterCodeBinding
import com.example.neobis_android_chapter8.model.Confirm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterCodeViewModel : ViewModel() {
    private lateinit var binding: FragmentEnterCodeBinding

    fun enterCode(fragment: Fragment, code: String) {
        val request = Confirm(code)
        val apiInterface = RetrofitInstance.api

        val call = apiInterface.confirm(request)
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(fragment.requireContext(), "Подтверждение прошло успешно", Toast.LENGTH_SHORT).show()
                    fragment.findNavController().navigate(R.id.action_enterCodeFragment_to_profileFragment)
                } else {
                    binding.errorMsg.isVisible = true
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(fragment.requireContext(), "Повторите попытку", Toast.LENGTH_SHORT).show()
            }
        })
    }
}