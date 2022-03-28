package com.narcissus.marketplace.core.navigation.destination

class SignInDestination(hasNavigatedFromUserScreen: Boolean) : NavDestination {
    override val url: String = "marketplace-app://sign-in/$hasNavigatedFromUserScreen"
}
