package com.narcissus.marketplace.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.model.dummyUser
import com.narcissus.marketplace.domain.repository.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class UserViewModel(
    private val userRepository: UserRepository,
) : ViewModel(), ContainerHost<UserState, UserSideEffect> {

    override val container: Container<UserState, UserSideEffect> =
        container(UserState(isLoading = true))

    val isUserAuthenticated = flow {
        emit(userRepository.isUserAuthenticated())
    }.shareIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        1,
    )

    init {
        val loadingState = UserState(
            isLoading = true,
            isUserAuthenticated = false,
            user = null,
        )

        val notAuthenticatedState = UserState(
            isLoading = false,
            isUserAuthenticated = false,
            user = null,
        )

        val loggedInState = UserState(
            isLoading = false,
            isUserAuthenticated = true,
            user = dummyUser,
        )

        viewModelScope.launch {
            while (true) {
                intent { reduce { loadingState } }
                delay(1000L)
                intent { reduce { notAuthenticatedState } }
                delay(2000L)
                intent { reduce { loggedInState } }
                delay(16000L)
            }
        }
    }

    fun onSignInClicked() {

    }

    fun toast(text: String) = intent {
        postSideEffect(UserSideEffect.Toast(text))
    }
}
