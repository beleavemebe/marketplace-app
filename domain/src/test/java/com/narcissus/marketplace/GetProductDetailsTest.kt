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
    private val productDetailsResultExpected = ActionResult.SuccessResult(
        mockk<ProductDetails>(relaxed = true) {
        },
    )
    private val productDetailsRepository = mockk<ProductsDetailsRepository> {
        coEvery { getProductDetailsById(productId) } returns productDetailsResultExpected
    }
    private val userRepository = mockk<UserRepository> {
        coEvery { writeToVisitedProducts(productDetailsResultExpected.data.toProductPreview()) } returns Unit
    }
    private val getProductDetails = GetProductDetails(productDetailsRepository, userRepository)

    @Test
    fun `get product details test`() {
        runBlocking {
            val result = getProductDetails(productId)
            Assert.assertTrue(result is ActionResult.SuccessResult)
            Assert.assertEquals(
                productDetailsResultExpected.data,
                (result as ActionResult.SuccessResult).data,
            )
        }
        coVerifyOrder {
            productDetailsRepository.getProductDetailsById(productId)
            userRepository.writeToVisitedProducts(productDetailsResultExpected.data.toProductPreview())
        }

    }
}
