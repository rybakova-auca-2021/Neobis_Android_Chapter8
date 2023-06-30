package com.example.neobis_android_chapter8.view.products

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
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
        binding.progressBar.visibility = View.VISIBLE
        myProductsViewModel.fetchProductList(
            onSuccess = { productList ->
                adapter.updateProduct(productList)
                if (productList.isEmpty()) {
                    binding.boxImg.isVisible = true
                    binding.msg.isVisible = true
                } else {
                    binding.boxImg.isVisible = false
                    binding.msg.isVisible = false
                }
                binding.progressBar.visibility = View.GONE
            },
            onError = {
                Toast.makeText(requireContext(), "Только зарегистрированный пользоатель имеет доступ к товарам", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }
        )
    }

    private fun setupRV() {
        adapter = RecyclerViewAdapter()
        val recyclerView = binding.rvProducts
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter.setOnItemClick(object : RecyclerViewAdapter.ListClickListener<Product> {
            override fun onClick(data: Product, position: Int) {
                val fragment = ProductDetailFragment().apply {
                    arguments = Bundle().apply { putParcelable("products", data) }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.hostFragment, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            override fun onThreeDotsClick(data: Product, position: Int) {
                val dialog = createBottomSheetDialog()
                val changeTextView = dialog.findViewById<TextView>(R.id.changeButton)
                changeTextView?.setOnClickListener {
                    dialog.dismiss()
                    findNavController().navigate(R.id.editProductFragment)
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
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        deleteButton.setOnClickListener {
            myProductsViewModel.deleteProduct(
                onSuccess = {
                    adapter.removeItem(position)
                    addCardViewToContainer()
                    dialog.dismiss()
                },
                onError = {
                    Toast.makeText(requireContext(), "Не удалось удалить товар", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                },
                productId = data.id
            )
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }


    private fun addCardViewToContainer() {
        val cardViewContent = LayoutInflater.from(requireContext()).inflate(R.layout.product_deleted_msg, null)
        val cardView = CardView(requireContext())
        cardView.addView(cardViewContent)

        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        binding.container.addView(cardView, layoutParams)

        cardView.postDelayed({
            binding.container.removeView(cardView)
        }, 1000)
    }
}