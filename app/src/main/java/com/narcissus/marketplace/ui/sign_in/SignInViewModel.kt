package com.narcissus.marketplace.ui.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.auth.SignInResult
import com.narcissus.marketplace.domain.usecase.SignInWithEmail
import com.narcissus.marketplace.domain.usecase.SignInWithGoogle
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    val signInWithEmail: SignInWithEmail,
    val signInWithGoogle: SignInWithGoogle,
) : ViewModel() {

    private val _signInResultFlow =
        MutableSharedFlow<SignInResult>(
            replay = 1,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
        )

    val signInResultFlow = _signInResultFlow.asSharedFlow()

    fun signInWithEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            val signInResult = signInWithEmail(email, password)
            _signInResultFlow.emit(signInResult)
        }
    }

    fun signInWithGoogleAccount(idToken: String) {
        viewModelScope.launch {
            val signInResult = signInWithGoogle(idToken)
            _signInResultFlow.emit(signInResult)
        }
    }
}
