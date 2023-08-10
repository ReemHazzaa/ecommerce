package com.extremeSolution.ecommerce.app.mappers

import android.content.Context
import com.extremeSolution.ecommerce.R
import com.extremeSolution.ecommerce.app.recyclerViewUtils.adapters.products.ProductsAdapter
import com.extremeSolution.ecommerce.app.utils.Constants

fun mapCategoryTitleToBgColor(category: String, context: Context): Int {
    return when (category) {
        Constants.VIEW_TYPE_MEN_TITLE -> context.getColor(R.color.men_bg_color)
        Constants.VIEW_TYPE_WOMEN_TITLE -> context.getColor(R.color.women_bg_color)
        Constants.VIEW_TYPE_JEWELRY_TITLE -> context.getColor(R.color.jewelery_bg_color)
        Constants.VIEW_TYPE_ELECTRONICS_TITLE -> context.getColor(R.color.electronics_bg_color)
        else -> context.getColor(R.color.electronics_bg_color)
    }
}

fun mapCategoryTitleToColor(category: String, context: Context): Int {
    return when (category) {
        Constants.VIEW_TYPE_MEN_TITLE -> context.getColor(R.color.men_txt_color)
        Constants.VIEW_TYPE_WOMEN_TITLE -> context.getColor(R.color.women_txt_color)
        Constants.VIEW_TYPE_JEWELRY_TITLE -> context.getColor(R.color.jewelery_txt_color)
        Constants.VIEW_TYPE_ELECTRONICS_TITLE -> context.getColor(R.color.electronics_txt_color)
        else -> context.getColor(R.color.electronics_txt_color)
    }
}

fun mapCategoryNameToViewTypeInt(category: String): Int {
    return when (category) {
        Constants.VIEW_TYPE_MEN_TITLE -> ProductsAdapter.VIEW_TYPE_MEN
        Constants.VIEW_TYPE_WOMEN_TITLE -> ProductsAdapter.VIEW_TYPE_WOMEN
        Constants.VIEW_TYPE_JEWELRY_TITLE -> ProductsAdapter.VIEW_TYPE_JEWELRY
        Constants.VIEW_TYPE_ELECTRONICS_TITLE -> ProductsAdapter.VIEW_TYPE_ELECTRONICS
        else -> ProductsAdapter.VIEW_TYPE_ELECTRONICS
    }
}