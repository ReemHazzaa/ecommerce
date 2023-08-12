package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.categoryProducts.productsMen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.app.ui.categories.CategoriesFragmentDirections
import com.extremeSolution.ecommerce.databinding.ItemCategoryMenHorizontalBinding
import com.extremeSolution.ecommerce.domain.models.product.Product

class ProductsMenAdapter(
    private val navController: NavController
) :
    RecyclerView.Adapter<ProductsMenAdapter.CustomViewHolder>() {

    private var data = emptyList<Product>()

    class CustomViewHolder(private val binding: ItemCategoryMenHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.item = product
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CustomViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemCategoryMenHorizontalBinding.inflate(layoutInflater, parent, false)
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
            val action =
                CategoriesFragmentDirections.actionCategoriesFragmentToProductDetailsFragment(currentItem.id)
            navController.navigate(action)
        }
    }

    fun setData(newList: List<Product>) {
        val categoriesDiffUtil = ProductsMenDiffUtil(data, newList)
        val diffUtilResult = DiffUtil.calculateDiff(categoriesDiffUtil)
        data = newList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}