package com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.products

import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.extremeSolution.ecommerce.app.mappers.mapCategoryNameToViewTypeInt
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders.CategoryElectronicsGridVH
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders.CategoryJeweleryGridVH
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders.CategoryMenGridVH
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.customViewHolders.CategoryWomenGridVH
import com.extremeSolution.ecommerce.app.ui.home.HomeFragmentDirections
import com.extremeSolution.ecommerce.domain.models.product.Product


class ProductsAdapter(
    private val navController: NavController
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_MEN = 1
        const val VIEW_TYPE_WOMEN = 2
        const val VIEW_TYPE_JEWELRY = 3
        const val VIEW_TYPE_ELECTRONICS = 4
    }

    private var data = emptyList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MEN -> CategoryMenGridVH.from(parent)
            VIEW_TYPE_WOMEN -> CategoryWomenGridVH.from(parent)
            VIEW_TYPE_JEWELRY -> CategoryJeweleryGridVH.from(parent)
            else -> CategoryElectronicsGridVH.from(parent)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = data[position]

        when (mapCategoryNameToViewTypeInt(currentItem.category)) {
            VIEW_TYPE_MEN -> (holder as CategoryMenGridVH).bind(currentItem)
            VIEW_TYPE_WOMEN -> (holder as CategoryWomenGridVH).bind(currentItem)
            VIEW_TYPE_JEWELRY -> (holder as CategoryJeweleryGridVH).bind(currentItem)
            VIEW_TYPE_ELECTRONICS -> (holder as CategoryElectronicsGridVH).bind(currentItem)
        }

        holder.itemView.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(currentItem.id)
            navController.navigate(action)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mapCategoryNameToViewTypeInt(data[position].category)
    }

    fun setData(newList: List<Product>) {
        val productsDiffUtil = ProductsDiffUtil(data, newList)
        val diffUtilResult = DiffUtil.calculateDiff(productsDiffUtil)
        data = newList
        diffUtilResult.dispatchUpdatesTo(this)

    }
}