package com.narcissus.marketplace.ui.sign_up.di

import com.narcissus.marketplace.core.navigation.destination.SignUpDestination
import org.koin.dsl.module

val signUpModule = module {
    factory { SignUpDestination }
}
