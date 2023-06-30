package com.example.neobis_android_chapter8.view.products

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.databinding.FragmentEditProductBinding
import com.example.neobis_android_chapter8.databinding.FragmentEditProfileBinding
import com.example.neobis_android_chapter8.databinding.FragmentProductDetailBinding
import com.example.neobis_android_chapter8.model.ProductModel.Product
import com.example.neobis_android_chapter8.viewModels.ProductViewModel.ReadProductViewModel

class EditProductFragment : Fragment() {
    private lateinit var binding: FragmentEditProductBinding
    private val editViewModel: EditProductViewModel by viewModels()
    private val infoViewModel: ReadProductViewModel by viewModels()

    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation()
        getProductFromArguments()
        getInfo()
    }

    private fun setupNavigation() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnReady.setOnClickListener {
            editViewModel.updateProduct(
                product.id,
                product,
                onSuccess = {
                    Toast.makeText(requireContext(),"Изменения сохранены!",Toast.LENGTH_SHORT).show()
                },
                onError = {
                    Toast.makeText(requireContext(),"Повторите попытку!",Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun getProductFromArguments() {
        arguments?.let {
            product = it.getParcelable("products") ?: throw IllegalArgumentException("Product argument not found")
        }
    }

    private fun getInfo() {
        infoViewModel.getProductData(product.id)
        infoViewModel.productData.observe(viewLifecycleOwner) { product ->
            product?.let {
                binding.inputName.text = Editable.Factory.getInstance().newEditable(it.title)
                binding.inputPrice.text = Editable.Factory.getInstance().newEditable(it.price)
                binding.inputShortDesc.text = Editable.Factory.getInstance().newEditable(it.short_description)
                binding.inputFullDesc.text = Editable.Factory.getInstance().newEditable(it.full_description)
            }
        }
    }
}