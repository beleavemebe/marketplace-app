package com.narcissus.marketplace.core.navigation

import androidx.navigation.NavOptions
import androidx.navigation.Navigator

interface MarketplaceCrossModuleNavigator {
    fun navigate(destination: NavDestination)
    fun navigate(destination: NavDestination, options: NavOptions)
    fun navigate(destination: NavDestination, extras: Navigator.Extras)
    fun navigate(destination: NavDestination, options: NavOptions, extras: Navigator.Extras)
}
