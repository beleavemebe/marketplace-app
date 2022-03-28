package com.narcissus.marketplace.ui.sign_in.di

import com.narcissus.marketplace.core.navigation.destination.SignInDestination
import org.koin.dsl.module

val signInModule = module {
    factory { (hasNavigatedFromUserScreen: Boolean) ->
        SignInDestination(hasNavigatedFromUserScreen)
    }
}
