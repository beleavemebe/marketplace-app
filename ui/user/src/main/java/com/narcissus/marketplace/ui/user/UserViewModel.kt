package com.narcissus.marketplace.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.auth.AuthState
import com.narcissus.marketplace.domain.usecase.GetAuthStateFlow
import com.narcissus.marketplace.domain.usecase.SignOut
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
            when (authState) {
                AuthState.Unknown ->
                    UserState(
                        isLoading = true,
                        isUserAuthenticated = false,
                        user = null,
                    )
                AuthState.NotAuthenticated ->
                    UserState(
                        isLoading = false,
                        isUserAuthenticated = false,
                        user = null,
                    )
                is AuthState.Authenticated ->
                    UserState(
                        isLoading = false,
                        isUserAuthenticated = true,
                        user = authState.user,
                    )
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOut.invoke()
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

    fun goToOrders() {
        toast("Orders")
    }

    fun goToBilling() {
        toast("Billing")
    }

    fun clearData() {
        toast("Clear data")
    }

    fun reportBug() {
        toast("Report bug")
    }

    fun goToSourceCode() {
        toast("Source code")
    }
}
