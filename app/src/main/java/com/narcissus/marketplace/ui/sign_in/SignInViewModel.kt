package com.narcissus.marketplace.ui.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.usecase.SignInWithEmail
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class SignInViewModel(
    val signInWithEmail: SignInWithEmail,
    // val signInWithGoogle:SignInWithGoogle
) : ViewModel() {

    private val _authFLow = MutableSharedFlow<AuthResult>(replay = 1, 1, BufferOverflow.DROP_OLDEST)
    val authFlow: SharedFlow<AuthResult> = _authFLow.asSharedFlow()
    fun signInWithEmailPassword(email: String, password: String) {
        if (email.isNotBlank() && password.isNotBlank())
            viewModelScope.launch { _authFLow.emit(signInWithEmail(email, password)) }
    }
}
