package com.narcissus.marketplace.ui.user

sealed class UserSideEffect {
    data class Toast(val text: String) : UserSideEffect()
}
