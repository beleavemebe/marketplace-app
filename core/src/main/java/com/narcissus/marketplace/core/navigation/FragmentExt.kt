package com.narcissus.marketplace.core.navigation

import androidx.fragment.app.Fragment

val Fragment.navigator: MarketplaceNavigator
    get() {
        return (requireActivity() as? MarketplaceNavigator)
            ?: throw IllegalStateException(
                "Host activity does not implement `MarketplaceNavigator` interface",
            )
    }
