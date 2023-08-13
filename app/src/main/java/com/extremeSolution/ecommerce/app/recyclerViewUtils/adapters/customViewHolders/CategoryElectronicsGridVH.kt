package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.app.extensions.loadImageWithPicasso
import com.extremeSolution.ecommerce.app.extensions.setDoubleValueToTextView
import com.extremeSolution.ecommerce.databinding.ItemCategoryElectronicsBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class CategoryElectronicsGridVH(private val binding: ItemCategoryElectronicsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.apply {
            tvPrice.setDoubleValueToTextView(product.price)
            tvTitle.text = product.title
            ivImage.loadImageWithPicasso(product.image)
        }
    }

    companion object {
        fun from(parent: ViewGroup): CategoryElectronicsGridVH {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCategoryElectronicsBinding.inflate(layoutInflater, parent, false)
            return CategoryElectronicsGridVH(binding)
        }
    }
}