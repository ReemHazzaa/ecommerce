package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.databinding.ItemCategoryJeweleryBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class CategoryJeweleryGridVH(private val binding: ItemCategoryJeweleryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.item = product
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CategoryJeweleryGridVH {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCategoryJeweleryBinding.inflate(layoutInflater, parent, false)
            return CategoryJeweleryGridVH(binding)
        }
    }
}