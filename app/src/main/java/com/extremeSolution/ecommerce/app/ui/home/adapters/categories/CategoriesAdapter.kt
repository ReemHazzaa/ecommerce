package com.extremeSolution.ecommerce.app.ui.home.adapters.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.databinding.ItemCategoryBinding

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CustomViewHolder>() {

    private var data = emptyList<String>()

    class CustomViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) {
            binding.item = category
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CustomViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
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

    fun setData(newList: List<String>) {
        val categoriesDiffUtil = CategoriesDiffUtil(data, newList)
        val diffUtilResult = DiffUtil.calculateDiff(categoriesDiffUtil)
        data = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}