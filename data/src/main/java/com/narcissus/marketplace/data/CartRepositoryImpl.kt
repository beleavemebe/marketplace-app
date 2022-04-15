package com.narcissus.marketplace.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.narcissus.marketplace.data.mapper.toBean
import com.narcissus.marketplace.data.mapper.toCartItem
import com.narcissus.marketplace.data.model.CartItemBean
import com.narcissus.marketplace.domain.model.CartItem
import com.narcissus.marketplace.domain.repository.CartRepository
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CartRepositoryImpl(
    private val cartRef: DatabaseReference,
) : CartRepository {
    override fun getCart(): Flow<List<CartItem>> =
        callbackFlow {
            val eventListener = createValueEventListener()
            cartRef.addValueEventListener(eventListener)

            awaitClose {
                cartRef.removeEventListener(eventListener)
            }
        }

    private fun ProducerScope<List<CartItem>>.createValueEventListener() =
        object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cart = snapshot.children.mapNotNull { child ->
                    child.getValue<CartItemBean>()
                        ?.toCartItem()
                }

                trySendBlocking(cart)
            }

            override fun onCancelled(error: DatabaseError) {}
        }

    override suspend fun getCartCost(): Int {
        return cartRef.get().await().children
            .mapNotNull { child ->
                child.getValue<CartItemBean>()
            }
            .filter { it.isSelected }
            .map { it.productPrice * it.amount }
            .reduceOrNull(Int::plus) ?: 0
    }

    override suspend fun addToCart(cartItem: CartItem) {
        cartRef.child(cartItem.productId)
            .setValue(cartItem.toBean())
    }

    override suspend fun addAllSelectedToCart(cartItems: List<CartItem>) {
        cartItems.onEach { cartItem ->
            val amount = getCartItemCount(cartItem.productId) + cartItem.amount
            val updatedCartItem = cartItem.copy(amount = amount)
            cartRef.child(cartItem.productId).setValue(updatedCartItem)
        }
    }

    private suspend fun getCartItemCount(productId: String) =
        cartRef.get().await().children.mapNotNull { child ->
            child.getValue<CartItemBean>()?.toCartItem()
        }.firstOrNull { it.productId == productId }?.amount ?: 0

    override suspend fun getCurrentCartSelected() =
        cartRef.get().await().children.mapNotNull { child ->
            child.getValue<CartItemBean>()?.toCartItem()
        }.filter { it.isSelected }

    override suspend fun removeFromCart(cartItem: CartItem) {
        cartRef.child(cartItem.productId)
            .removeValue()
    }

    override suspend fun setCartItemSelected(cartItem: CartItem, selected: Boolean) {
        val newItem = cartItem.copy(isSelected = selected)
        cartRef.child(cartItem.productId)
            .setValue(newItem.toBean())
    }

    override suspend fun setCartItemAmount(cartItem: CartItem, amount: Int) {
        val newItem = cartItem.copy(amount = amount)
        cartRef.child(cartItem.productId)
            .setValue(newItem.toBean())
    }

    override suspend fun selectAllCartItems(isSelected: Boolean) {
        cartRef.get().addOnSuccessListener { snapshot ->
            val newItems = snapshot.children.mapNotNull { child ->
                child.getValue<CartItemBean>()
                    ?.copy(isSelected = isSelected)
            }

            val newChildren = newItems.associateBy { it.productId }
            cartRef.updateChildren(newChildren)
        }
    }

    override suspend fun deleteSelectedItems() {
        val transactionHandler = createDeleteSelectedTransactionHandler()
        cartRef.runTransaction(transactionHandler)
    }

    private fun createDeleteSelectedTransactionHandler() =
        object : Transaction.Handler {
            override fun doTransaction(currentData: MutableData) =
                runDeleteSelectedTransaction(currentData)

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?,
            ) {
                handleDeleteSelectedTransactionCompletion(error, committed, currentData)
            }
        }

    private fun runDeleteSelectedTransaction(currentData: MutableData): Transaction.Result {
        currentData.children.forEach { data ->
            val bean = data.getValue<CartItemBean>() ?: return@forEach
            data.value = bean.takeUnless { it.isSelected == true }
        }

        return Transaction.success(currentData)
    }

    private fun handleDeleteSelectedTransactionCompletion(
        error: DatabaseError?,
        committed: Boolean,
        currentData: DataSnapshot?,
    ) {
        // todo: handle error
    }
}
