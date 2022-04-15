package com.narcissus.marketplace.data.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.narcissus.marketplace.data.persistence.model.ProductEntity
import com.narcissus.marketplace.data.persistence.model.SearchHistoryEntity

@Database(
    entities = [ProductEntity::class,SearchHistoryEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun searchHistoryDao():SearchHistoryDao

    companion object {
        const val DATABASE_NAME = "marketplace-app.db"
    }
}
