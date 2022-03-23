package com.narcissus.marketplace.ui.sing_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.usecase.SignUpWithEmail
import com.narcissus.marketplace.domain.util.AuthResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    val signUpWithEmail: SignUpWithEmail,
) : ViewModel() {
    private val _authResultFlow = MutableSharedFlow<AuthResult>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val authResultFlow = _authResultFlow.asSharedFlow()

    fun signUpWithEmailPassword(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            val authResult = signUpWithEmail(fullName, email, password)
            _authResultFlow.emit(authResult)
        }
    }
}
