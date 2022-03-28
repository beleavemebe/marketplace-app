package com.narcissus.marketplace

import com.narcissus.marketplace.domain.repository.UserRepository
import com.narcissus.marketplace.domain.usecase.SignUpWithEmail
import com.narcissus.marketplace.domain.util.AuthResult
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SignUpWithEmailTest {
    private val emptyString = ""
    private val correctEmail = "correctemail@somemail.com"
    private val incorrectEmail1 = "incorrectEmail"
    private val incorrectEmail2 = "incorrectEmail.somemail@com"
    private val incorrectPass = "12345"
    private val correctPass = "123456"
    private val correctFullName = "Ivan"
    private val signUpSuccess = mockk<AuthResult.SignUpSuccess>()
    private val userRepository = mockk<UserRepository> {
        coEvery { signUpWithEmail(any(), any(), any()) } returns signUpSuccess
    }
    val signUpWithEmail = SignUpWithEmail(userRepository)
    @Test
    fun `should return sign up error if data incorrect`() {
        runBlocking {
            Assert.assertEquals(
                AuthResult.SignUpEmptyInput,
                signUpWithEmail(emptyString, correctEmail, correctPass),
            )
            Assert.assertEquals(
                AuthResult.SignUpWrongEmail,
                signUpWithEmail(correctFullName, emptyString, correctPass),
            )
            Assert.assertEquals(
                AuthResult.SignUpWrongEmail,
                signUpWithEmail(correctFullName, incorrectEmail1, correctPass),
            )
            Assert.assertEquals(
                AuthResult.SignUpWrongEmail,
                signUpWithEmail(correctFullName, incorrectEmail2, correctPass),
            )
            Assert.assertEquals(
                AuthResult.SignUpToShortPassword,
                signUpWithEmail(correctFullName, correctEmail, emptyString),
            )
            Assert.assertEquals(
                AuthResult.SignUpToShortPassword,
                signUpWithEmail(correctFullName, correctEmail, incorrectPass),
            )
        }
        coVerifySequence { userRepository.signUpWithEmail(any(), any(), any()) wasNot Called }
    }

    @Test
    fun `should call sign up with email and return result if data correct`() {
        runBlocking {
            Assert.assertEquals(
                signUpSuccess,
                signUpWithEmail(correctFullName, correctEmail, correctPass),
            )
        }
        coVerifySequence{ userRepository.signUpWithEmail(correctFullName, correctEmail, correctPass) }
    }


}
