package com.narcissus.marketplace.domain.util

import com.narcissus.marketplace.domain.model.UserProfile

sealed class AuthResult {
    data class SignInSuccess(val userProfile: UserProfile) : AuthResult()
    data class SignUpSucces(val userProfile: UserProfile) : AuthResult()
    object SignOutSucces : AuthResult()
    object SignInWrongPasswordOrEmail : AuthResult()
    object NotAuthrized : AuthResult()
    object Error : AuthResult()
//    object SignUpWrongEmail:AuthResult() ЕСЛИ НАДО
//    object SignUpWrongPassword:AuthResult()
//    object SignUpToShortPassword:AuthResult()
}
