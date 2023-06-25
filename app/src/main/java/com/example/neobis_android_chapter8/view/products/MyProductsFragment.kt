package com.example.neobis_android_chapter8.view.products

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neobis_android_chapter8.HomeActivity
import com.example.neobis_android_chapter8.R
import com.example.neobis_android_chapter8.adapter.RecyclerViewAdapter
import com.example.neobis_android_chapter8.databinding.FragmentMyProductsBinding
import com.example.neobis_android_chapter8.model.ProductModel.Product
import com.example.neobis_android_chapter8.viewModels.ProductViewModel.MyProductsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class MyProductsFragment : Fragment() {
    private lateinit var binding: FragmentMyProductsBinding
    private lateinit var adapter: RecyclerViewAdapter
    private val myProductsViewModel: MyProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMyProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as HomeActivity).hide()
        setupNavigation()
        setupRV()
        showProductList()
    }

    private fun setupNavigation() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_myProductsFragment_to_profileFragment)
        }
    }
    private fun showProductList() {
        myProductsViewModel.fetchProductList(binding, adapter, this)
    }

    private fun setupRV() {
        adapter = RecyclerViewAdapter()
        val recyclerView = binding.rvProducts
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter.setOnItemClick(object : RecyclerViewAdapter.ListClickListener<Product> {
            override fun onClick(data: Product, position: Int) {
                findNavController().navigate(R.id.productDetailFragment)
                // TODO перенести все данные
            }
            override fun onThreeDotsClick(data: Product, position: Int) {
                val dialog = createBottomSheetDialog()
                val changeTextView = dialog.findViewById<TextView>(R.id.changeButton)
                changeTextView?.setOnClickListener {
                    dialog.dismiss()
                    findNavController().navigate(R.id.addProductFragment)
                }
                val deleteTextView = dialog.findViewById<TextView>(R.id.deleteButton)
                deleteTextView?.setOnClickListener {
                    dialog.dismiss()
                    showConfirmationDialog(data, position)
                }
                dialog.show()
            }

        })
    }

    private fun createBottomSheetDialog(): BottomSheetDialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogStyle)
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        return dialog
    }

    private fun showConfirmationDialog(data: Product, position: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delete_product_dialog, null)

        val deleteButton = dialogView.findViewById<Button>(R.id.button_delete_product)
        val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)

        val dialogBuilder = AlertDialog.Builder(requireContext(), R.style.LightDialogTheme)
            .setView(dialogView)

        val dialog = dialogBuilder.create()
        dialog.show()

        deleteButton.setOnClickListener {
            myProductsViewModel.deleteProduct(binding, this, position, adapter)
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}