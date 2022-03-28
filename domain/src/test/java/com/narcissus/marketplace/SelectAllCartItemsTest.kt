package com.narcissus.marketplace

import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.usecase.SelectAllCartItems
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SelectAllCartItemsTest {
    private val isSelected = mockk<Boolean>(relaxed = true)
    private val cartRepository = mockk<CartRepository> {
        coEvery { selectAllCartItems(any()) } returns Unit
    }
    private val selectAllCartItems = SelectAllCartItems(cartRepository)

    @Test
    fun `should call all cart item selection with correct attribute`() {
        runBlocking {
            selectAllCartItems(isSelected)
        }
        coVerify(exactly = 1) { cartRepository.selectAllCartItems(isSelected) }
    }
}
