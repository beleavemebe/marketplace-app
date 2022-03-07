package com.narcissus.marketplace.data.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.narcissus.marketplace.data.persistence.model.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        const val DATABASE_NAME = "marketplace-app.db"
    }
}
