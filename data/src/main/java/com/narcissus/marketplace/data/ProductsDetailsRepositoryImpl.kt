package com.narcissus.marketplace.data

import com.narcissus.marketplace.apiclient.api.model.ProductDetailsResponse
import com.narcissus.marketplace.apiclient.api.model.ReviewResponseData
import com.narcissus.marketplace.apiclient.api.model.SimilarProductsResponseData
import com.narcissus.marketplace.apiclient.api.service.ApiService
import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.model.Review
import com.narcissus.marketplace.model.SimilarProduct
import com.narcissus.marketplace.repository.ProductsDetailsRepository
import com.narcissus.marketplace.util.ActionResult

internal class ProductsDetailsRepositoryImpl(private val apiService: ApiService) :
    ProductsDetailsRepository {
    override suspend fun getProductDetailsById(productId: String): ActionResult<ProductDetails> {
        val productDetails = apiService.getProductDetails(productId)
        return ActionResult.SuccessResult(mapDetails(productDetails))
    }

    private fun mapDetails(detailsResponse: ProductDetailsResponse) = with(detailsResponse.data) {
        ProductDetails(
            id,
            productImg,
            price,
            name,
            departmentName,
            type,
            stock,
            color,
            material,
            description,
            rating,
            sales,
            reviewsList.map(ReviewResponseData::toReview),
            similarProductsList.map(SimilarProductsResponseData::toSimilarProducts)
        )
    }
}

private fun SimilarProductsResponseData.toSimilarProducts(): SimilarProduct =
    SimilarProduct(id, icon, price, name, departmentName, type, stock, rating)

private fun ReviewResponseData.toReview(): Review =
    Review(reviewId, author, details, rating, reviewAuthorIcon)
