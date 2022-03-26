package com.narcissus.marketplace.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.usecase.GetAuthStateFlow
import com.narcissus.marketplace.domain.usecase.SignOut
import com.narcissus.marketplace.domain.util.AuthState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class UserViewModel(
    getAuthStateFlow: GetAuthStateFlow,
    private val signOut: SignOut,
) : ViewModel(), ContainerHost<UserState, UserSideEffect> {

    override val container: Container<UserState, UserSideEffect> =
        container(UserState(isLoading = true))

    init {
        getAuthStateFlow()
            .onEach(::updateScreenState)
            .launchIn(viewModelScope)
    }

    private fun updateScreenState(authState: AuthState) = intent {
        reduce {
            UserState(
                isLoading = false,
                isUserAuthenticated = authState.user != null,
                user = authState.user,
            )
        }
    }

    fun onSignOutClicked() {
        viewModelScope.launch {
            signOut()
        }
    }

    fun onSignInClicked() = intent {
        postSideEffect(UserSideEffect.NavigateToSignIn)
    }

    fun toast(text: String) = intent {
        postSideEffect(UserSideEffect.Toast(text))
    }

    fun switchTheme(checked: Boolean) = intent {
        postSideEffect(UserSideEffect.SwitchTheme(checked))
    }
}
