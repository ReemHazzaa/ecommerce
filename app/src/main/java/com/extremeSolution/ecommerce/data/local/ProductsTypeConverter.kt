package com.extremeSolution.ecommerce.data.local

import androidx.room.TypeConverter
import com.extremeSolution.ecommerce.domain.models.product.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductsTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun productRatingToString(rating: Rating): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    fun stringToProductRating(string: String): Rating {
        val type = object : TypeToken<Rating>() {}.type
        return gson.fromJson(string, type)
    }
}