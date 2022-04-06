package com.narcissus.marketplace.data.firebase.di

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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

    single {
        Firebase.auth
    }

    factory(
        qualifier<Qualifiers.UserUid>()
    ) {
        get<FirebaseAuth>().currentUser?.uid ?: "anonymous"
    }

    single(
        qualifier<Qualifiers.CartReference>()
    ) {
        val userId = get<String>(qualifier<Qualifiers.UserUid>())
        Log.d("marketplace-debug", "user id: $userId")
        get<FirebaseDatabase>().getReference("${Constants.CHILD_CART}/$userId")
    }
    single(
        qualifier<Qualifiers.OrdersReference>()
    ) {
        val userId = get<String>(qualifier<Qualifiers.UserUid>())
        Log.d("marketplace-debug", "user id: $userId")
        get<FirebaseDatabase>().getReference("${Constants.CHILD_ORDERS}/$userId")
    }
}
