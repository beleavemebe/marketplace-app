package com.narcissus.marketplace.data.firebase.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.narcissus.marketplace.data.firebase.Constants
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val firebaseModule = module {
    single {
        Firebase.database(Constants.DATABASE_URL)
    }

    single(qualifier<CartReference>()) {
        get<FirebaseDatabase>().getReference(Constants.CHILD_CART)
    }
}
