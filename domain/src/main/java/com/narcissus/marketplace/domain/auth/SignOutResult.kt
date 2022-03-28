package com.narcissus.marketplace.domain.auth

sealed class SignOutResult {
    object Error : SignOutResult()
    object Success : SignOutResult()
}
