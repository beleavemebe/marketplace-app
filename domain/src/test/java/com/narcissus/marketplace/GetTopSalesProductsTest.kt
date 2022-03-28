package com.narcissus.marketplace

import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.repository.ProductsPreviewRepository
import com.narcissus.marketplace.domain.usecase.GetTopRatedProducts
import com.narcissus.marketplace.domain.usecase.GetTopSalesProducts
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetTopSalesProductsTest {
    private val expectedProductPreviews = mockk<List<ProductPreview>>()
    private val productsPreviewRepository = mockk<ProductsPreviewRepository> {
        coEvery { getProductsTopSales() } returns expectedProductPreviews
    }
    private val  getTopSalesProducts = GetTopSalesProducts(productsPreviewRepository)
    @Test
    fun `should return actual top rated products`() {
        runBlocking {
            Assert.assertEquals(expectedProductPreviews,getTopSalesProducts())
        }
        coVerifySequence { productsPreviewRepository.getProductsTopSales() }
    }
}
