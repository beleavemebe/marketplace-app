package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.usecase.GetCart
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetCartTest {
    private val exceptedResult = mockk<Flow<List<CartItem>>>()
    private val cartRepository = mockk<CartRepository> {
        coEvery { getCart() } returns exceptedResult
    }
    private val getCart = GetCart(cartRepository)
    private val result = getCart()

    @Test
    fun `should return actual cart`() {

        runBlocking {

            Assert.assertEquals(exceptedResult, result)
        }
        coVerify(exactly = 1) { cartRepository.getCart() }
    }
}
