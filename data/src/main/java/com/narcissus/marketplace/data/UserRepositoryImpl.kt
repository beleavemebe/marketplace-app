package com.narcissus.marketplace.data

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.narcissus.marketplace.data.mapper.toProductPreview
import com.narcissus.marketplace.data.persistence.database.ProductDao
import com.narcissus.marketplace.data.persistence.model.ProductEntity
import com.narcissus.marketplace.domain.auth.AuthState
import com.narcissus.marketplace.domain.auth.SignInResult
import com.narcissus.marketplace.domain.auth.SignOutResult
import com.narcissus.marketplace.domain.auth.SignUpResult
import com.narcissus.marketplace.domain.model.ProductPreview
import com.narcissus.marketplace.domain.model.User
import com.narcissus.marketplace.domain.model.UserProfile
import com.narcissus.marketplace.domain.repository.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

internal class UserRepositoryImpl(
    private val productsDao: ProductDao,
    private val firebaseAuth: FirebaseAuth,
) : UserRepository {
    override val recentlyVisitedProducts: Flow<List<ProductPreview>> =
        productsDao.getProducts()
            .map { entities ->
                entities.map(ProductEntity::toProductPreview)
            }

    override suspend fun writeToVisitedProducts(productPreview: ProductPreview) {
        productsDao.insertProduct(productPreview.toProductEntity())
    }

    override suspend fun addCard(cardNumber: Long, svv: Int, expirationDate: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserData(): User {
        TODO("Not yet implemented")
    }

    override suspend fun isUserAuthenticated(): Boolean = firebaseAuth.currentUser != null

    override val authStateFlow: Flow<AuthState> =
        callbackFlow {
            trySendBlocking(AuthState.Unknown)

            val listener = FirebaseAuth.AuthStateListener { auth ->
                val currentUser = auth.currentUser?.toUserProfile()
                if (currentUser != null) {
                    trySendBlocking(AuthState.Authenticated(currentUser))
                } else {
                    trySendBlocking(AuthState.NotAuthenticated)
                }
            }

            firebaseAuth.addAuthStateListener(listener)

            awaitClose {
                firebaseAuth.removeAuthStateListener(listener)
            }
        }

    override suspend fun signInWithEmail(email: String, password: String): SignInResult {
        val currentUser = firebaseAuth.currentUser
        return if (currentUser != null) {
            SignInResult.Success(currentUser.toUserProfile())
        } else try {
            trySignInWithEmail(email, password)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            SignInResult.WrongPassword
        } catch (e: FirebaseAuthInvalidUserException) {
            SignInResult.UserNotFound
        } catch (e: FirebaseAuthException) {
            SignInResult.Error
        }
    }

    private suspend fun trySignInWithEmail(
        email: String,
        password: String,
    ): SignInResult {
        val signedInUser = firebaseAuth.signInWithEmailAndPassword(email, password).await().user
        return if (signedInUser != null) {
            SignInResult.Success(signedInUser.toUserProfile())
        } else {
            SignInResult.Error
        }
    }

    override suspend fun signUpWithEmail(
        fullName: String,
        email: String,
        password: String,
    ): SignUpResult {
        val currentUser = firebaseAuth.currentUser
        return if (currentUser != null) {
            SignUpResult.Success(currentUser.toUserProfile())
        } else try {
            trySignUpWithEmail(email, password, fullName)
        } catch (e: FirebaseException) {
            SignUpResult.Error
        }
    }

    private suspend fun trySignUpWithEmail(
        email: String,
        password: String,
        fullName: String,
    ): SignUpResult {
        val newUser = firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
        val updateRequest = userProfileChangeRequest {
            displayName = fullName
        }

        return if (newUser == null) {
            SignUpResult.Error
        } else {
            newUser.updateProfile(updateRequest)
            SignUpResult.Success(newUser.toUserProfile())
        }
    }

    override suspend fun signOut(): SignOutResult {
        return try {
            firebaseAuth.signOut()
            SignOutResult.Success
        } catch (e: Exception) {
            SignOutResult.Error
        }
    }

    override suspend fun signInWithGoogle(idToken: String): SignInResult {
        val user = firebaseAuth.currentUser
        return if (user != null) {
            SignInResult.Success(user.toUserProfile())
        } else try {
            trySignInWithGoogle(idToken)
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            SignInResult.WrongPassword
        } catch (e: FirebaseAuthInvalidUserException) {
            SignInResult.UserNotFound
        } catch (e: FirebaseAuthException) {
            SignInResult.Error
        }
    }

    private suspend fun trySignInWithGoogle(idToken: String): SignInResult {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        val signedInUser = firebaseAuth.signInWithCredential(firebaseCredential).await().user
        val profile = signedInUser?.toUserProfile()
        return if (profile != null) {
            SignInResult.Success(profile)
        } else {
            SignInResult.Error
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

    private fun FirebaseUser.toUserProfile(): UserProfile {
        return UserProfile(uid, displayName, email!!, photoUrl.toString())
    }
}
