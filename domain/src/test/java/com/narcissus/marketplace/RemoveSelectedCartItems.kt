package com.narcissus.marketplace

import com.narcissus.marketplace.domain.repository.CartRepository
import com.narcissus.marketplace.domain.usecase.RemoveSelectedCartItems
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RemoveSelectedCartItems {
    private val cartRepository = mockk<CartRepository> {
        coEvery { deleteSelectedItems() } returns Unit
    }
    val removeSelectedFromCart = RemoveSelectedCartItems(cartRepository)

    @Test
    fun `should call removal of selected items`() {
        runBlocking {
            removeSelectedFromCart()
        }
        coVerify(exactly = 1) { removeSelectedFromCart() }
    }
}
