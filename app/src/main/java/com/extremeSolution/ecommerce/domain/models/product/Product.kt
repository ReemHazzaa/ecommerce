package com.extremeSolution.ecommerce.domain.models.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.extremeSolution.ecommerce.app.Constants.PRODUCTS_TABLE

@Entity(tableName = PRODUCTS_TABLE)
data class Product(
    val category: String,
    val description: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)