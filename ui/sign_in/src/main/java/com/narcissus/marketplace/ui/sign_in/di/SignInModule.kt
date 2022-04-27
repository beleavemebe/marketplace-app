package com.narcissus.marketplace.ui.sign_in.di

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.narcissus.marketplace.ui.sign_in.SignInViewModel
import com.narcissus.marketplace.ui.sign_in.sign_up.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module

val signInModule = module {
    viewModel { SignInViewModel(get(), get()) }
    viewModel { SignUpViewModel(get()) }

    fun Scope.defaultWebClientId() = get<String>(
        qualifier<SignInQualifiers.DefaultWebClientId>()
    )

    single {
        GoogleSignIn.getClient(
            androidContext(),
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(defaultWebClientId())
                .requestEmail()
                .build(),
        )
    }

    single {
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(defaultWebClientId())
                    .setFilterByAuthorizedAccounts(true)
                    .build(),
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}
