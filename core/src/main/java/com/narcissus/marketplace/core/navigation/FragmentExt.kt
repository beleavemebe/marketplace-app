package com.narcissus.marketplace.core.navigation

import androidx.fragment.app.Fragment

val Fragment.navigator: MarketplaceCrossModuleNavigator
    get() {
        return (requireActivity() as? MarketplaceCrossModuleNavigator)
            ?: throw IllegalStateException(
                "Host activity does not implement `MarketplaceCrossModuleNavigator` interface",
            )
    }
