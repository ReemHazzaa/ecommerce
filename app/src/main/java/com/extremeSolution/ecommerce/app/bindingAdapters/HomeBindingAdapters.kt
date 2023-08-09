package com.extremeSolution.ecommerce.app.bindingAdapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.extremeSolution.ecommerce.R
import com.squareup.picasso.Picasso

class HomeBindingAdapters {
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
    }
}