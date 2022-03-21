package com.narcissus.marketplace.data

import android.util.Patterns
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.narcissus.marketplace.data.mapper.toProductPreview
import com.narcissus.marketplace.data.persistence.database.ProductDao
import com.narcissus.marketplace.data.persistence.model.ProductEntity
import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.model.User
import com.narcissus.marketplace.domain.model.UserProfile
import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.util.ActionResult
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

internal class UserRepositoryImpl(
    private val productsDao: ProductDao,
    private val firebaseAuth: FirebaseAuth,
) : UserRepository {
    override fun getRecentlyVisitedProducts(): Flow<List<ProductPreview>> {
        return productsDao.getProducts().map { entities ->
            entities.map(ProductEntity::toProductPreview)
        }
    }

    override suspend fun writeToVisitedProducts(productPreview: ProductPreview) {
        productsDao.insertProduct(productPreview.toProductEntity())
    }

    override suspend fun addCard(cardNumber: Long, svv: Int, expirationDate: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(): ActionResult<User> {
        TODO("Not yet implemented")
    }

    override suspend fun isUserAuthenticated(): Boolean = firebaseAuth.currentUser != null

    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        if (!checkEmailFormatValidity(email)) {
            return AuthResult.WrongEmail
        }
        var currentUser = firebaseAuth.currentUser
        return currentUser?.toAuthResult()
            ?: try {
                currentUser = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
                currentUser?.toAuthResult() ?: AuthResult.SignInWrongPasswordOrEmail
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                AuthResult.SignInWrongPasswordOrEmail
            } catch (e: FirebaseAuthInvalidUserException) {
                AuthResult.SignInWrongPasswordOrEmail
            } catch (e: FirebaseAuthException) {
                AuthResult.Error
            }
    }

    private fun checkEmailFormatValidity(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): AuthResult {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithGoogle() {
        TODO("Not yet implemented")
    }
}

private fun ProductPreview.toProductEntity(): ProductEntity {
    return ProductEntity(
        id,
        icon,
        price,
        name,
        department,
        type,
        stock,
        color,
        material,
        rating,
        sales,
    )
}

private fun FirebaseUser.toAuthResult(): AuthResult {

    return AuthResult.SignInSuccess(
        UserProfile(
            uid, displayName, email!!, photoUrl.toString(),
        ),
    )
}
