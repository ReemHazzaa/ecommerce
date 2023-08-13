package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.app.extensions.loadImageWithPicasso
import com.extremeSolution.ecommerce.app.extensions.setDoubleValueToTextView
import com.extremeSolution.ecommerce.databinding.ItemCartBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class CartAdapter :
    RecyclerView.Adapter<CartAdapter.CustomViewHolder>() {

    private var data = emptyList<Product>()
    private var onClickListener: OnClickListener? = null

    class CustomViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                ivImage.loadImageWithPicasso(product.image)
                tvTitle.text = product.title
                tvPrice.setDoubleValueToTextView(product.price)
            }
        }

        companion object {
            fun from(parent: ViewGroup): CustomViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemCartBinding.inflate(layoutInflater, parent, false)
                return CustomViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder.from(parent)

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, currentItem)
            }
        }
    }

    fun setData(newList: List<Product>) {
        val categoriesDiffUtil = CartDiffUtil(data, newList)
        val diffUtilResult = DiffUtil.calculateDiff(categoriesDiffUtil)
        data = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, product: Product)
    }
}