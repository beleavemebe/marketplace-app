package com.narcissus.marketplace.data.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "search_history")
data class SearchHistoryEntity (
    @PrimaryKey
    val productName:String,
    val created: Long = System.currentTimeMillis()
    )
