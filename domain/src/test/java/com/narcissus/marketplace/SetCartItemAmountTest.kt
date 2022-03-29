package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.usecase.SetCartItemAmount
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SetCartItemAmountTest {
    private val expectedCartItem = mockk<CartItem>()
    private val amount = mockk<Int>(relaxed = true)
    private val cartRepository = mockk<CartRepository> {
        coEvery { setCartItemAmount(expectedCartItem, amount) } returns Unit
    }
    private val setCartItemAmount = SetCartItemAmount(cartRepository)

    @Test
    fun `should set cart item amount`() {
        runBlocking {
            setCartItemAmount(expectedCartItem, amount)
        }
        coVerify(exactly = 1) { cartRepository.setCartItemAmount(expectedCartItem, amount) }
    }
}
