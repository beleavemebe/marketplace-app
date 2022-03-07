package com.narcissus.marketplace.data.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    val id: String = "",
    val icon: String = "",
    val price: Int = 0,
    val name: String = "",
    val category: String = "",
    val type: String = "",
    val stock: Int = 0,
    val color: String = "",
    val material: String = "",
    val rating: Int = 0,
    val sales: Int = 0,
    val created: Long = System.currentTimeMillis(),
)
