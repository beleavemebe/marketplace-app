package com.narcissus.marketplace.data

import com.narcissus.marketplace.model.DetailsAbout
import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.model.Review
import com.narcissus.marketplace.repository.remote.ProductsDetailsRepository
import com.narcissus.marketplace.util.ActionResult
import org.koin.core.component.KoinComponent

internal class ProductsDetailsRepositoryImpl : ProductsDetailsRepository,KoinComponent {
    override suspend fun getProductDetailsById(productId: String): ActionResult<ProductDetails> {
        val result = ProductDetails(
            "1",
            "https://www.apple.com/newsroom/images/tile-images/Apple_new-macbook-air-wallpaper-screen_03182020.jpg.og.jpg?202112021825",
            1299,
            "Apple Macbook Pro 13 Core i9 8 RAM GB 512GB SSD Geforce RTX 3060",
            "Laptops",
            1012,
            listOf(DetailsAbout.Type("Laptop"), DetailsAbout.Color("Silver"), DetailsAbout.Material("Stainless Steel"), DetailsAbout.Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip.")),
            4,
            15,
            listOf(
                Review("1", "author", "Nulla dolorum est rerum in officiis libero", 1),
                Review("2", "author", "Nulla dolorum est rerum in officiis libero", 2),
                Review("3", "author", "Nulla dolorum est rerum in officiis libero", 3),
                Review("4", "author", "Nulla dolorum est rerum in officiis libero", 4),
                Review("5", "author", "Nulla dolorum est rerum in officiis libero", 5)
            ),
            listOf(
                ProductPreview("1", "", 1449, "Apple MacBook Pro 13", "", "", 752, "", "", 3, 152),
                ProductPreview("2", "", 1299, "Apple MacBook Air 13", "", "", 1021, "", "", 5, 196),
                ProductPreview("3", "", 2199, "Apple MacBook Pro 16", "", "", 128, "", "", 4, 65),
            ),
        )
        return ActionResult.SuccessResult(result)
    }
}
