package com.narcissus.marketplace.core.navigation.destination

class ProductDetailsDestination(productId: String) : NavDestination {
    override val url = "marketplace-app://product/${productId}"
}
