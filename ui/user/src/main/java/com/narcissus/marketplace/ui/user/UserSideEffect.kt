package com.narcissus.marketplace.ui.user

sealed class UserSideEffect {
    data class Toast(val text: String) : UserSideEffect()
    data class SwitchTheme(val checked: Boolean) : UserSideEffect()
    object NavigateToSignIn : UserSideEffect()
}
