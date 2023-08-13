package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.app.extensions.loadImageWithPicasso
import com.extremeSolution.ecommerce.app.extensions.setDoubleValueToTextView
import com.extremeSolution.ecommerce.databinding.ItemCategoryMenBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class CategoryMenGridVH(private val binding: ItemCategoryMenBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        binding.apply {
            tvPrice.setDoubleValueToTextView(product.price)
            tvTitle.text = product.title
            ivImage.loadImageWithPicasso(product.image)
        }
    }

    companion object {
        fun from(parent: ViewGroup): CategoryMenGridVH {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCategoryMenBinding.inflate(layoutInflater, parent, false)
            return CategoryMenGridVH(binding)
        }
    }
}