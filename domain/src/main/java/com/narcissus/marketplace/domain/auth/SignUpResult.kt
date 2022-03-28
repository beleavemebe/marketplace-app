package com.narcissus.marketplace.domain.auth

import com.narcissus.marketplace.domain.model.UserProfile

sealed class SignUpResult {
    object Error : SignUpResult()
    object BlankFullName : SignUpResult()
    object InvalidEmail : SignUpResult()
    data class InvalidPassword(val failedRequirements: List<PasswordRequirement>) : SignUpResult()
    data class Success(val userProfile: UserProfile) : SignUpResult()
}
