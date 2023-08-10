package com.extremeSolution.ecommerce.app.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.extremeSolution.ecommerce.R
import com.extremeSolution.ecommerce.app.mappers.mapCategoryTitleToBgColor
import com.extremeSolution.ecommerce.app.mappers.mapCategoryTitleToColor
import com.squareup.picasso.Picasso

class AppBindingAdapters {
    companion object {

        @JvmStatic
        @BindingAdapter("setDoubleValueToTextView")
        fun setDoubleValueToTextView(textView: TextView, doubleValue: Double) {
            textView.text = doubleValue.toString()
        }

        @JvmStatic
        @BindingAdapter("loadImageWithPicasso")
        fun loadImageWithPicasso(imageView: ImageView, url: String) {
            Picasso.with(imageView.context).load(url).fit().centerCrop()
                .placeholder(R.drawable.image_gallery)
                .error(R.drawable.error)
                .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("setCategoryTvTxtColor")
        fun setCategoryTvTxtColor(textView: TextView, category: String) {
            val categoryColor = mapCategoryTitleToColor(category, textView.context)
            textView.setTextColor(categoryColor)
        }

        @JvmStatic
        @BindingAdapter("setCategoryRvItemColors")
        fun setCategoryRvItemColors(textView: TextView, category: String) {
            val categoryTxtColor = mapCategoryTitleToColor(category, textView.context)
            val categoryBgColor = mapCategoryTitleToBgColor(category, textView.context)
            textView.setTextColor(categoryTxtColor)
            textView.setBackgroundColor(categoryBgColor)
        }
    }
}