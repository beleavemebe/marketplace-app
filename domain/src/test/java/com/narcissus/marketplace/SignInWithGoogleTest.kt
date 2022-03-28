package com.narcissus.marketplace

import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.usecase.SignInWithEmail
import com.narcissus.marketplace.domain.usecase.SignInWithGoogle
import com.narcissus.marketplace.domain.util.AuthResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SignInWithGoogleTest {
        private val signInSuccess = mockk<AuthResult.SignInSuccess>()
        private val idToken = "123"
        private val userRepository = mockk<UserRepository> {
            coEvery { signInWithGoogle(idToken)} returns signInSuccess
        }
        private val signInWithGoogle = SignInWithGoogle(userRepository)
        @Test
        fun `should call sign in with email and return result`(){
            runBlocking {
                signInWithGoogle(idToken)
            }
            coVerify(exactly = 1) { userRepository.signInWithGoogle(idToken) }
        }
}
