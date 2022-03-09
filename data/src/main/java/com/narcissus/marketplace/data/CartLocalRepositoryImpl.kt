package com.narcissus.marketplace.data

import com.narcissus.marketplace.model.CartItem
import com.narcissus.marketplace.model.ProductPreview
import com.narcissus.marketplace.repository.local.CartLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CartLocalRepositoryImpl : CartLocalRepository {

    private val sampleCartItem =
        ProductPreview("1", "https://c.tenor.com/UjdeUF--bBkAAAAS/sussy.gif", 1449, "Fake item", "", "", 752, "", "", 3, 152)

    private val sampleCartItemTwo =
        ProductPreview("2", "https://c.tenor.com/UjdeUF--bBkAAAAS/sussy.gif", 400, "Apple Watch", "", "", 240, "", "", 5, 100)

    private val items = MutableStateFlow(
        listOf(
            CartItem(sampleCartItem, 1, isSelected = false),
            CartItem(sampleCartItemTwo, 1, isSelected = false),
        )
    )

    override fun getCart(): Flow<List<CartItem>> {
        return items
    }

    override suspend fun addToCart(cartItem: CartItem) {
        items.value = items.value + cartItem
    }

    override suspend fun removeFromCart(cartItem: CartItem) {
        items.value = items.value - cartItem
    }

    override suspend fun setCartItemSelected(cartItem: CartItem, selected: Boolean) {

        val newList = items.value.map { item ->
            if (item == cartItem) {
                item.copy(isSelected = selected)
            } else {
                item
            }
        }

        items.value = newList
    }

    override suspend fun setCartItemAmount(cartItem: CartItem, amount: Int) {

        val newList = items.value.map { item ->
            if (item == cartItem) {
                item.copy(count = amount)
            } else {
                item
            }
        }

        items.value = newList
    }

    override suspend fun selectAllCartItems(isSelected: Boolean) {
        val newList = items.value.map { item ->
            item.copy(isSelected = isSelected)
        }
        items.value = newList
    }

    override suspend fun deleteSelectedItems() {
        val updatedList: MutableList<CartItem> = mutableListOf()

        items.value.map { item ->
            if (!item.isSelected) updatedList.add(item)
        }

        items.value = updatedList
    }
}
