package com.extremeSolution.ecommerce.domain.models.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.extremeSolution.ecommerce.app.utils.Constants.PRODUCTS_TABLE

@Entity(tableName = PRODUCTS_TABLE)
data class Product(
    @PrimaryKey
    var id: Int,
    var category: String,
    var description: String,
    var image: String,
    var price: Double,
    var rating: Rating,
    var title: String,
    var inCart: Boolean = false
)