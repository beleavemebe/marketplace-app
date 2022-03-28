package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.ProductDetails
import com.narcissus.marketplace.domain.model.toCartItem
import com.narcissus.marketplace.domain.model.toProductPreview
import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.usecase.AddToCart
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AddToCartTest {

    private val cartRepository = mockk<CartRepository> {
        coEvery { addToCart(any()) } returns Unit
    }
    val addToCart = AddToCart(cartRepository)
    private val productDetails = mockk<ProductDetails>(relaxed = true)
    private val cartItemExpected = productDetails.toProductPreview().toCartItem()
    @Test
    fun `should add product preview to cart`() {
        runBlocking { addToCart(productDetails.toProductPreview()) }
        coVerifySequence{
            cartRepository.addToCart(cartItemExpected)
        }
    }

    @Test
    fun `should add product details to cart`() {

        runBlocking { addToCart(productDetails) }
        coVerifySequence{
            cartRepository.addToCart(cartItemExpected)
        }
    }

}
