package com.narcissus.marketplace.data

import com.narcissus.marketplace.apiclient.api.model.ProductDetailsResponse
import com.narcissus.marketplace.apiclient.api.model.ReviewResponseData
import com.narcissus.marketplace.apiclient.api.model.SimilarProductsResponseData
import com.narcissus.marketplace.apiclient.api.service.ApiService
import com.narcissus.marketplace.domain.model.ProductDetails
import com.narcissus.marketplace.domain.model.Review
import com.narcissus.marketplace.domain.model.SimilarProduct
import com.narcissus.marketplace.domain.repository.ProductsDetailsRepository

internal class ProductsDetailsRepositoryImpl(private val apiService: ApiService) :
    ProductsDetailsRepository {
    override suspend fun getProductDetailsById(productId: String): ProductDetails {
        val productDetails = apiService.getProductDetails(productId)
        return mapDetails(productDetails)
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
        )
    }
}

private fun SimilarProductsResponseData.toSimilarProducts(): SimilarProduct =
    SimilarProduct(id, icon, price, name, departmentName, type, stock, rating)

private fun ReviewResponseData.toReview(): Review =
    Review(reviewId, author, details, rating, reviewAuthorIcon)
