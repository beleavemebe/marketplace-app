package com.narcissus.marketplace.di

import com.narcissus.marketplace.repository.local.CartLocalRepository
import com.narcissus.marketplace.usecase.GetCart
import com.narcissus.marketplace.usecase.GetCartCost
import com.narcissus.marketplace.usecase.GetCartItemsAmount
import com.narcissus.marketplace.usecase.RemoveFromCart
import com.narcissus.marketplace.usecase.RemoveSelectedCartItems
import com.narcissus.marketplace.usecase.SelectAllCartItems
import com.narcissus.marketplace.usecase.SetCartItemAmount
import com.narcissus.marketplace.usecase.SetCartItemSelected
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// TODO: replace with Koin direct injection
object ServiceLocator : KoinComponent {
    private val cartLocalRepositoryImpl: CartLocalRepository by inject()
    val getCart = GetCart(cartLocalRepositoryImpl)
    val getCartCost = GetCartCost(cartLocalRepositoryImpl)
    val getCartItemsAmount = GetCartItemsAmount(cartLocalRepositoryImpl)
    val removeFromCart = RemoveFromCart(cartLocalRepositoryImpl)
    val setCartItemSelected = SetCartItemSelected(cartLocalRepositoryImpl)
    val setCartItemAmount = SetCartItemAmount(cartLocalRepositoryImpl)
    val selectAllCartItems = SelectAllCartItems(cartLocalRepositoryImpl)
    val removeSelectedCartItems = RemoveSelectedCartItems(cartLocalRepositoryImpl)
}
