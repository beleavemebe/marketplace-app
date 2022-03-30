package com.narcissus.marketplace.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.auth.SignUpResult
import com.narcissus.marketplace.domain.usecase.SignUpWithEmail
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    val signUpWithEmail: SignUpWithEmail,
) : ViewModel() {
    private val _signUpResultFlow = MutableSharedFlow<SignUpResult>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val signUpResultFlow = _signUpResultFlow.asSharedFlow()

    fun signUpWithEmailPassword(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            val authResult = signUpWithEmail(fullName, email, password)
            _signUpResultFlow.emit(authResult)
        }
    }
}
