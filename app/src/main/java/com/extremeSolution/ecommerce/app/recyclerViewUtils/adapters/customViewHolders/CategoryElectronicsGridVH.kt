package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.databinding.ItemCategoryElectronicsBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class CategoryElectronicsGridVH(private val binding: ItemCategoryElectronicsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.item = product
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CategoryElectronicsGridVH {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCategoryElectronicsBinding.inflate(layoutInflater, parent, false)
            return CategoryElectronicsGridVH(binding)
        }
    }
}