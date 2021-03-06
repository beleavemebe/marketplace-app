package com.narcissus.marketplace.di

import com.narcissus.marketplace.domain.usecase.AddToCart
import com.narcissus.marketplace.domain.usecase.GetAuthStateFlow
import com.narcissus.marketplace.domain.usecase.GetCart
import com.narcissus.marketplace.domain.usecase.GetCartCost
import com.narcissus.marketplace.domain.usecase.GetCartCostFlow
import com.narcissus.marketplace.domain.usecase.GetCartItemsAmount
import com.narcissus.marketplace.domain.usecase.GetCheckout
import com.narcissus.marketplace.domain.usecase.GetDepartments
import com.narcissus.marketplace.domain.usecase.GetOrderList
import com.narcissus.marketplace.domain.usecase.GetPeopleAreBuyingProducts
import com.narcissus.marketplace.domain.usecase.GetProductDetails
import com.narcissus.marketplace.domain.usecase.GetRandomProducts
import com.narcissus.marketplace.domain.usecase.GetRecentlyVisitedProducts
import com.narcissus.marketplace.domain.usecase.GetSelectedCartItems
import com.narcissus.marketplace.domain.usecase.GetSimilarProducts
import com.narcissus.marketplace.domain.usecase.GetTopRatedProducts
import com.narcissus.marketplace.domain.usecase.GetTopSalesProducts
import com.narcissus.marketplace.domain.usecase.MakeAnOrder
import com.narcissus.marketplace.domain.usecase.RemoveFromCart
import com.narcissus.marketplace.domain.usecase.RemoveSelectedCartItems
import com.narcissus.marketplace.domain.usecase.RestoreCartItems
import com.narcissus.marketplace.domain.usecase.SelectAllCartItems
import com.narcissus.marketplace.domain.usecase.SetCartItemAmount
import com.narcissus.marketplace.domain.usecase.SetCartItemSelected
import com.narcissus.marketplace.domain.usecase.SignInWithEmail
import com.narcissus.marketplace.domain.usecase.SignInWithGoogle
import com.narcissus.marketplace.domain.usecase.SignOut
import com.narcissus.marketplace.domain.usecase.SignUpWithEmail
import com.narcissus.marketplace.domain.usecase.ValidateCard
import org.koin.dsl.module

val domainModule = module {
    factory { GetTopRatedProducts(get()) }
    factory { GetTopSalesProducts(get()) }
    factory { GetRandomProducts(get()) }
    factory { GetRecentlyVisitedProducts(get()) }
    factory { GetProductDetails(get(), get()) }
    factory { MakeAnOrder(get(), get()) }
    factory { GetDepartments(get()) }
    factory { GetOrderList(get()) }
    factory { GetCart(get()) }
    factory { GetCartItemsAmount(get()) }
    factory { GetCartCost(get()) }
    factory { GetCartCostFlow(get()) }
    factory { GetSelectedCartItems(get()) }
    factory { SetCartItemAmount(get()) }
    factory { SetCartItemSelected(get()) }
    factory { SelectAllCartItems(get()) }
    factory { RemoveSelectedCartItems(get()) }
    factory { RemoveFromCart(get()) }
    factory { RestoreCartItems(get()) }
    factory { AddToCart(get()) }
    factory { SignInWithEmail(get()) }
    factory { SignInWithGoogle(get()) }
    factory { SignUpWithEmail(get()) }
    factory { SignOut(get()) }
    factory { GetAuthStateFlow(get()) }
    factory { GetCheckout(get()) }
    factory { ValidateCard() }
    factory { GetSimilarProducts(get()) }
    factory { GetPeopleAreBuyingProducts(get()) }
}
