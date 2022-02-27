package com.narcissus.marketplace.ui.product_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.model.Review
import com.narcissus.marketplace.ui.product_details.model.MainProductData

class ProductDetailsViewModel : ViewModel() {
    private val _isReviewsListExpanded = MutableLiveData(false)
    val isReviewsListExpanded: LiveData<Boolean> = _isReviewsListExpanded
    private val _mainProductDataLiveData = MutableLiveData(
        MainProductData(
            "1",
            "https://www.apple.com/newsroom/images/tile-images/Apple_new-macbook-air-wallpaper-screen_03182020.jpg.og.jpg?202112021825",
            1299,
            "Apple Macbook Pro 13 Core i9 8 RAM GB 512GB SSD Geforce RTX 3060",
            "Laptops",
            1012,
            15,
            4,
            "Laptop", "Silver",
            "Stainless Steel",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip.",
            listOf(
                ProductPreview("1", "", 1449, "Apple MacBook Pro 13", "", "", 752, "", "", 3, 152),
                ProductPreview("2", "", 1299, "Apple MacBook Air 13", "", "", 1021, "", "", 5, 196),
                ProductPreview("3", "", 2199, "Apple MacBook Pro 16", "", "", 128, "", "", 4, 65),
            )
        )
    )

    val mainProductDataLiveData: LiveData<MainProductData> = _mainProductDataLiveData
    private val _reviewsProductDataLiveData = MutableLiveData(
        listOf(
            Review("1", "author", "Nulla dolorum est rerum in officiis libero", 1),
            Review("2", "author", "Nulla dolorum est rerum in officiis libero", 2),
        )
    )
    val reviewsProductData: LiveData<List<Review>> = _reviewsProductDataLiveData

    private fun setAllReviews() {
        _reviewsProductDataLiveData.value = listOf(
            Review("1", "author", "Nulla dolorum est rerum in officiis libero", 1),
            Review("2", "author", "Nulla dolorum est rerum in officiis libero", 2),
            Review("3", "author", "Nulla dolorum est rerum in officiis libero", 3),
            Review("4", "author", "Nulla dolorum est rerum in officiis libero", 4),
            Review("5", "author", "Nulla dolorum est rerum in officiis libero", 5)
        )
    }

    private fun hideReviews() {
        _reviewsProductDataLiveData.value = listOf(
            Review("1", "author", "Nulla dolorum est rerum in officiis libero", 1),
            Review("2", "author", "Nulla dolorum est rerum in officiis libero", 2),
        )
    }

    fun changeReviewsListState() {
        _isReviewsListExpanded.value = !isReviewsListExpanded.value!!
        if (isReviewsListExpanded.value == true) {
            setAllReviews()
        } else hideReviews()

    }

}
