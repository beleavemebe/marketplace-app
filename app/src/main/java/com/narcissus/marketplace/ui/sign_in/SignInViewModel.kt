package com.narcissus.marketplace.ui.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.usecase.SignInWithEmail
import com.narcissus.marketplace.domain.usecase.SignInWithGoogle
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    val signInWithEmail: SignInWithEmail,
    val signInWithGoogle: SignInWithGoogle
) : ViewModel() {

    private val _authResultFlow =
        MutableSharedFlow<AuthResult>(
            replay = 1,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

    val authResultFlow = _authResultFlow.asSharedFlow()

    fun signInWithEmailPassword(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank()) {
            viewModelScope.launch {
                val authResult = signInWithEmail(email, password)
                _authResultFlow.emit(authResult)
            }
        }
    }

    fun signInWithGoogleAccount(idToken: String) {
        viewModelScope.launch {
            val authResult = signInWithGoogle(idToken)
            _authResultFlow.emit(authResult)
        }
    }
}
