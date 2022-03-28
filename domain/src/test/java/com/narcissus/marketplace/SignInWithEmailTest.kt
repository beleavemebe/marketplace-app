package com.narcissus.marketplace

import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.usecase.SignInWithEmail
import com.narcissus.marketplace.domain.util.AuthResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SignInWithEmailTest {
    private val signInSuccess = mockk<AuthResult.SignInSuccess>()
    private val email = "email"
    private val pass = "pass"
    private val userRepository = mockk<UserRepository> {
        coEvery { signInWithEmail(email,pass)} returns signInSuccess
    }
    private val signInWithEmail = SignInWithEmail(userRepository)
    @Test
    fun `should call sign in with email and return result`(){
        runBlocking {
            signInWithEmail(email,pass)
        }
        coVerify(exactly = 1) { userRepository.signInWithEmail(email,pass) }
    }
}
