package com.narcissus.marketplace.data.persistence.di

import androidx.room.Room
import com.narcissus.marketplace.data.persistence.database.AppDatabase
import com.narcissus.marketplace.data.persistence.database.AppDatabase.Companion.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val persistenceModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single {
        get<AppDatabase>().productDao()
    }
    single {
        get<AppDatabase>().searchHistoryDao()
    }
}
