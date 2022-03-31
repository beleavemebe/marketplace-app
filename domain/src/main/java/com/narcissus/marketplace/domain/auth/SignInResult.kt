package com.narcissus.marketplace.domain.auth

import com.narcissus.marketplace.domain.model.UserProfile

sealed class SignInResult {
    object Error : SignInResult()
    object InvalidEmail : SignInResult()
    object BlankPassword : SignInResult()
    object WrongPassword : SignInResult()
    object UserNotFound : SignInResult()
    data class Success(val userProfile: UserProfile) : SignInResult()
}
