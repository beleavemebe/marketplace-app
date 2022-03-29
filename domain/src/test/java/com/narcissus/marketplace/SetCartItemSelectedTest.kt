package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.usecase.SetCartItemSelected
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SetCartItemSelectedTest {
    private val expectedCartItem = mockk<CartItem>()
    private val selection = mockk<Boolean>(relaxed = true)
    private val cartRepository = mockk<CartRepository> {
        coEvery { setCartItemSelected(expectedCartItem, selection) } returns Unit
    }
    private val setCartItemAmount = SetCartItemSelected(cartRepository)

    @Test
    fun `should set cart item selection`() {
        runBlocking {
            setCartItemAmount(expectedCartItem, selection)
        }
        coVerify(exactly = 1) { cartRepository.setCartItemSelected(expectedCartItem, selection) }
    }
}
