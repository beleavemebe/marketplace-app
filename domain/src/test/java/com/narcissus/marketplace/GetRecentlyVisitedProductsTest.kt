package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.usecase.GetRecentlyVisitedProducts
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetRecentlyVisitedProductsTest {
    private val expectedRecentlyVisitedProductsPreviews = flowOf(mockk<List<ProductPreview>>())
    private val userRepository = mockk<UserRepository> {
        coEvery { recentlyVisitedProducts } returns expectedRecentlyVisitedProductsPreviews
    }
    private val getRecentlyVisitedProducts = GetRecentlyVisitedProducts(userRepository)

    @Test
    fun `should return actual recently visited products`() {
        runBlocking {
            Assert.assertEquals(
                expectedRecentlyVisitedProductsPreviews,
                getRecentlyVisitedProducts(),
            )
        }
        coVerify(exactly = 1) { userRepository.recentlyVisitedProducts }
    }
}
