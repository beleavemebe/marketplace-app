package com.narcissus.marketplace.domain.usecase

import com.narcissus.marketplace.domain.model.ProductDetails
import com.narcissus.marketplace.domain.model.toProductPreview
import com.narcissus.marketplace.domain.repository.ProductsDetailsRepository
import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.util.ActionResult

class GetProductDetails(
    private val productsDetailsRepository: ProductsDetailsRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(productId: String): ActionResult<ProductDetails> {
        val productDetails = productsDetailsRepository
            .getProductDetailsById(productId)
            .getOrThrow()

        userRepository.writeToVisitedProducts(productDetails.toProductPreview())

        return ActionResult.SuccessResult(productDetails)
    }
}
