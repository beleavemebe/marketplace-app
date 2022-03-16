package com.narcissus.marketplace.usecase

import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.model.toProductPreview
import com.narcissus.marketplace.repository.ProductsDetailsRepository
import com.narcissus.marketplace.repository.UserRepository
import com.narcissus.marketplace.util.ActionResult

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
