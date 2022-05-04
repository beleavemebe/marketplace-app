package com.narcissus.marketplace.di

import com.narcissus.marketplace.R
import com.narcissus.marketplace.ui.sign_in.di.SignInQualifiers
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val appModule = module {
    factory(qualifier<SignInQualifiers.DefaultWebClientId>()) {
        androidContext().getString(R.string.default_web_client_id)
    }
}
