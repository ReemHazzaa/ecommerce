package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.categoryProducts.productsJewelery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.databinding.ItemCategoryJeweleryHorizontalBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class ProductsJeweleryAdapter :
    RecyclerView.Adapter<ProductsJeweleryAdapter.CustomViewHolder>() {

    private var data = emptyList<Product>()

    class CustomViewHolder(private val binding: ItemCategoryJeweleryHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.item = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CustomViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemCategoryJeweleryHorizontalBinding.inflate(layoutInflater, parent, false)
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
    }

    fun setData(newList: List<Product>) {
        val categoriesDiffUtil = ProductsJeweleryDiffUtil(data, newList)
        val diffUtilResult = DiffUtil.calculateDiff(categoriesDiffUtil)
        data = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}