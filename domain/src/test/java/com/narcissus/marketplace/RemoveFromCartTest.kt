package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.usecase.RemoveFromCart
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoveFromCartTest {
    private val cartItem = mockk<CartItem>()
    private val cartRepository = mockk<CartRepository> {
        coEvery { removeFromCart(cartItem) } returns Unit
    }
    val removeFromCart = RemoveFromCart(cartRepository)

    @Test
    fun `should remove item from cart`() {
        runBlocking {
            removeFromCart(cartItem)
        }
        coVerify(exactly = 1) { cartRepository.removeFromCart(cartItem) }
    }
}
