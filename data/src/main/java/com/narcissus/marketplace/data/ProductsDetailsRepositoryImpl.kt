package com.narcissus.marketplace.data

import com.narcissus.marketplace.apiclient.api.model.ProductDetailsResponse
import com.narcissus.marketplace.apiclient.api.model.SimilarProductsResponseData
import com.narcissus.marketplace.apiclient.api.service.ApiService
import com.narcissus.marketplace.model.DetailsAbout
import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.model.Review
import com.narcissus.marketplace.repository.remote.ProductsDetailsRepository
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
            stock,
            aboutList = listOf(
                DetailsAbout.Type(type),
                DetailsAbout.Color(color),
                DetailsAbout.Material(material),
                DetailsAbout.Description(description)
            ),
            rating,
            sales,
            reviewsList.map { Review(it.reviewId, it.author, it.details, it.rating, it.reviewAuthorIcon) },
            similarProductsList.map(SimilarProductsResponseData::toSimilarProducts)
        )
    }
}

private fun SimilarProductsResponseData.toSimilarProducts(): ProductPreview =
    ProductPreview(id, icon, price, name, departmentName, type, stock, "", "", rating, sales)
