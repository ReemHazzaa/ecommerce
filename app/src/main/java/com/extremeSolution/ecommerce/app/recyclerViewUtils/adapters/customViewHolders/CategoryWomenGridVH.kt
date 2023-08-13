package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.app.extensions.loadImageWithPicasso
import com.extremeSolution.ecommerce.app.extensions.setDoubleValueToTextView
import com.extremeSolution.ecommerce.databinding.ItemCategoryWomenBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class CategoryWomenGridVH(private val binding: ItemCategoryWomenBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.apply {
            tvPrice.setDoubleValueToTextView(product.price)
            tvTitle.text = product.title
            ivImage.loadImageWithPicasso(product.image)
        }
    }

    companion object {
        fun from(parent: ViewGroup): CategoryWomenGridVH {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCategoryWomenBinding.inflate(layoutInflater, parent, false)
            return CategoryWomenGridVH(binding)
        }
    }
}