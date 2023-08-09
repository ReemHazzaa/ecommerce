package com.extremeSolution.ecommerce.app.ui.home.adapters.products

import androidx.recyclerview.widget.DiffUtil
import com.extremeSolution.ecommerce.domain.models.product.Product

class ProductsDiffUtil(
    private val oldList: List<Product>,
    private val newList: List<Product>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}