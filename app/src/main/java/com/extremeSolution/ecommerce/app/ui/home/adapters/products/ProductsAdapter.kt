package com.extremeSolution.ecommerce.app.ui.home.adapters.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.app.Constants.VIEW_TYPE_ELECTRONICS_TITLE
import com.extremeSolution.ecommerce.app.Constants.VIEW_TYPE_JEWELRY_TITLE
import com.extremeSolution.ecommerce.app.Constants.VIEW_TYPE_MEN_TITLE
import com.extremeSolution.ecommerce.app.Constants.VIEW_TYPE_WOMEN_TITLE
import com.extremeSolution.ecommerce.databinding.ItemCategoryElectronicsBinding
import com.extremeSolution.ecommerce.databinding.ItemCategoryJeweleryBinding
import com.extremeSolution.ecommerce.databinding.ItemCategoryMenBinding
import com.extremeSolution.ecommerce.databinding.ItemCategoryWomenBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class ProductsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_MEN = 1
        const val VIEW_TYPE_WOMEN = 2
        const val VIEW_TYPE_JEWELRY = 3
        const val VIEW_TYPE_ELECTRONICS = 4
    }

    private var data = emptyList<Product>()

    class CategoryMenViewHolder(private val binding: ItemCategoryMenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.item = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryMenViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryMenBinding.inflate(layoutInflater, parent, false)
                return CategoryMenViewHolder(binding)
            }
        }
    }

    class CategoryWomenViewHolder(private val binding: ItemCategoryWomenBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.item = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryWomenViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryWomenBinding.inflate(layoutInflater, parent, false)
                return CategoryWomenViewHolder(binding)
            }
        }
    }

    class CategoryJeweleryViewHolder(private val binding: ItemCategoryJeweleryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.item = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryJeweleryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryJeweleryBinding.inflate(layoutInflater, parent, false)
                return CategoryJeweleryViewHolder(binding)
            }
        }
    }

    class CategoryElectronicsViewHolder(private val binding: ItemCategoryElectronicsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.item = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CategoryElectronicsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryElectronicsBinding.inflate(layoutInflater, parent, false)
                return CategoryElectronicsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MEN -> CategoryMenViewHolder.from(parent)
            VIEW_TYPE_WOMEN -> CategoryWomenViewHolder.from(parent)
            VIEW_TYPE_JEWELRY -> CategoryJeweleryViewHolder.from(parent)
            else -> CategoryElectronicsViewHolder.from(parent)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = data[position]

        when (mapCategoryNameToViewTypeInt(currentItem.category)) {
            VIEW_TYPE_MEN -> (holder as CategoryMenViewHolder).bind(currentItem)
            VIEW_TYPE_WOMEN -> (holder as CategoryWomenViewHolder).bind(currentItem)
            VIEW_TYPE_JEWELRY -> (holder as CategoryJeweleryViewHolder).bind(currentItem)
            VIEW_TYPE_ELECTRONICS -> (holder as CategoryElectronicsViewHolder).bind(currentItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mapCategoryNameToViewTypeInt(data[position].category)
    }

    private fun mapCategoryNameToViewTypeInt(category: String): Int {
        return when (category) {
            VIEW_TYPE_MEN_TITLE -> VIEW_TYPE_MEN
            VIEW_TYPE_WOMEN_TITLE -> VIEW_TYPE_WOMEN
            VIEW_TYPE_JEWELRY_TITLE -> VIEW_TYPE_JEWELRY
            VIEW_TYPE_ELECTRONICS_TITLE -> VIEW_TYPE_ELECTRONICS
            else -> VIEW_TYPE_ELECTRONICS
        }
    }

    fun setData(newList: List<Product>) {
        val productsDiffUtil = ProductsDiffUtil(data, newList)
        val diffUtilResult = DiffUtil.calculateDiff(productsDiffUtil)
        data = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}