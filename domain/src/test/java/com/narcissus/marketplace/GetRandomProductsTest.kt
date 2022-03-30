package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository
import com.narcissus.marketplace.domain.usecase.GetRandomProducts
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetRandomProductsTest {
    private val expectedProductPreviews = mockk<List<ProductPreview>>()
    private val productsPreviewRepository = mockk<ProductsPreviewRepository> {
        coEvery { getProductsRandom() } returns expectedProductPreviews
    }
    private val getRandomProducts = GetRandomProducts(productsPreviewRepository)

    @Test
    fun `should return actual random products`() {
        runBlocking {
            Assert.assertEquals(expectedProductPreviews, getRandomProducts())
        }
        coVerify(exactly = 1) { productsPreviewRepository.getProductsRandom() }
    }
}
