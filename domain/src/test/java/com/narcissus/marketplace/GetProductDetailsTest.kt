package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.ProductDetails
import com.narcissus.marketplace.domain.model.toProductPreview
import com.narcissus.marketplace.domain.repository.ProductsDetailsRepository
import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.usecase.GetProductDetails
import com.narcissus.marketplace.domain.util.ActionResult
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetProductDetailsTest {
    private val productId = "1"
    private val productDetailsResultExpected = mockk<ProductDetails>(relaxed = true)
    private val productDetailsRepository = mockk<ProductsDetailsRepository> {
        coEvery { getProductDetailsById(productId) } returns productDetailsResultExpected
    }
    private val userRepository = mockk<UserRepository> {
        coEvery { writeToVisitedProducts(productDetailsResultExpected.toProductPreview()) } returns Unit
    }
    private val getProductDetails = GetProductDetails(productDetailsRepository, userRepository)

    @Test
    fun `should return actual details and save it to visited products`() {
        runBlocking {
            val result = getProductDetails(productId)
            Assert.assertEquals(
                productDetailsResultExpected,
                result,
            )
        }
        coVerifyOrder {
            productDetailsRepository.getProductDetailsById(productId)
            userRepository.writeToVisitedProducts(productDetailsResultExpected.toProductPreview())
        }

    }
}
