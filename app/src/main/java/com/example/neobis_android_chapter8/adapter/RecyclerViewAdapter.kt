package com.example.neobis_android_chapter8.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neobis_android_chapter8.databinding.CardBinding
import com.example.neobis_android_chapter8.model.ProductModel.Product

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var products = emptyList<Product>()
    var onClickListener: ListClickListener<Product>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(product, position)
        }
    }

    override fun getItemCount() = products.size

    fun setOnItemClick(listClickListener: ListClickListener<Product>) {
        this.onClickListener = listClickListener
    }

    fun updateProduct(newList: List<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffCallback(products, newList))
        products = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem(position: Int) {
        if (position >= 0 && position < products.size) {
            val updatedList = products.toMutableList()
            updatedList.removeAt(position)
            products = updatedList
            notifyItemRemoved(position)
        }
    }

    inner class ViewHolder(private val binding: CardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                Glide.with(image).load(product.images).into(image)
                title.text = product.title
                price.text = product.price.toString()

                binding.threeDots.setOnClickListener {
                    onClickListener?.onThreeDotsClick(product, adapterPosition)
                }
            }
        }
    }

    interface ListClickListener<T> {
        fun onClick(data: T, position: Int)
        fun onThreeDotsClick(data: T, position: Int)
    }

    class ProductDiffCallback(
        private val oldList: List<Product>,
        private val newList: List<Product>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}