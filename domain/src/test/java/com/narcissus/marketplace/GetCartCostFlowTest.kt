package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.usecase.GetCartCostFlow
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetCartCostFlowTest {

    @Test
    fun `should return 0 if list is empty`() {
        val cartResultEmpty: Flow<List<CartItem>> =
            flowOf(
                listOf(),
            )
        val exceptedResultOnEmptyList = 0
        val cartRepository = mockk<CartRepository> {
            coEvery { getCart() } returns cartResultEmpty
        }
        val getCartCost = GetCartCostFlow(cartRepository)
        runBlocking {
            val result = getCartCost().last()
            Assert.assertEquals(exceptedResultOnEmptyList, result)
        }
        verify(exactly = 1) { cartRepository.getCart() }
    }

    @Test
    fun `should return sum if list is not empty`() {
        val cartResultNotEmpty: Flow<List<CartItem>> =
            flowOf(
                listOf(
                    mockk {
                        every { productPrice } returns 10
                        every { isSelected } returns true
                        every { amount } returns 2
                    },
                    mockk {
                        every { productPrice } returns 20
                        every { isSelected } returns true
                        every { amount } returns 1
                    },
                    mockk {
                        every { productPrice } returns 200
                        every { isSelected } returns false
                        every { amount } returns 10
                    },
                    mockk {
                        every { productPrice } returns 200
                        every { isSelected } returns true
                        every { amount } returns 0
                    },
                ),
            )
        val exceptedResultOnNotEmptyList = 40
        val cartRepository = mockk<CartRepository> {
            coEvery { getCart() } returns cartResultNotEmpty
        }
        val getCartCost = GetCartCostFlow(cartRepository)
        runBlocking {
            val result = getCartCost().last()
            Assert.assertEquals(exceptedResultOnNotEmptyList, result)
        }
        verify(exactly = 1) { cartRepository.getCart() }
    }
}
