package com.narcissus.marketplace.domain.util

import com.narcissus.marketplace.domain.model.UserProfile

// todo: decompose to SignInResult and SignUpResult
sealed class AuthResult {
    data class SignInSuccess(val userProfile: UserProfile) : AuthResult()
    data class SignUpSuccess(val userProfile: UserProfile) : AuthResult()
    object SignOutSuccess : AuthResult()
    object SignInWrongPasswordOrEmail : AuthResult()
    object WrongEmail : AuthResult()
    object NotAuthorized : AuthResult()
    object Error : AuthResult()
    object SignUpWrongEmail : AuthResult()
    object SignUpToShortPassword : AuthResult()
    object SignUpEmptyInput : AuthResult()
}
