package com.narcissus.marketplace.ui.user

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.narcissus.marketplace.core.navigation.destination.OrdersDestination
import com.narcissus.marketplace.core.navigation.destination.SignInDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.Constants
import com.narcissus.marketplace.ui.user.composables.Loading
import com.narcissus.marketplace.ui.user.composables.UserScreen
import com.narcissus.marketplace.ui.user.composables.YouAreNotLoggedIn
import com.narcissus.marketplace.ui.user.theme.DefaultTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

class UserFragment : Fragment() {
    private val sharedPref by lazy {
        requireActivity().getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val viewModel: UserViewModel = getViewModel()
            DefaultTheme {
                UserScreenContent(viewModel)
            }
        }
    }

    @Composable
    private fun UserScreenContent(viewModel: UserViewModel) {
        viewModel.collectSideEffect(::handleSideEffect)
        val state = viewModel.collectAsState().value
        when {
            state.isLoading ->
                Loading()

            state.isUserAuthenticated == false ->
                YouAreNotLoggedIn(viewModel::onSignInClicked)

            state.user != null ->
                UserScreen(
                    viewModel = viewModel,
                    userProfile = state.user,
                    isAppInDarkTheme = isAppInDarkTheme(),
                )
        }
    }

    private fun isAppInDarkTheme(): Boolean {
        return sharedPref.getBoolean(Constants.THEME_KEY, false)
    }

    private fun handleSideEffect(sideEffect: UserSideEffect) {
        when (sideEffect) {
            is UserSideEffect.Toast -> toast(sideEffect.text)
            is UserSideEffect.SwitchTheme -> switchTheme(sideEffect.checked)
            is UserSideEffect.NavigateToSignIn -> navigateToSignIn()
            is UserSideEffect.NavigateToOrders -> navigateToOrders()
            is UserSideEffect.ViewSourceCode -> viewSourceCode()
        }
    }

    private fun navigateToSignIn() {
        val signInDestination = SignInDestination(hasNavigatedFromUserScreen = true)
        navigator.navigate(signInDestination)
    }

    private fun navigateToOrders() {
        navigator.navigate(OrdersDestination)
    }

    private fun viewSourceCode() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SOURCE_CODE_URL))
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            requireContext().startActivity(intent)
        }
    }

    private fun switchTheme(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        sharedPref.edit {
            putBoolean(Constants.THEME_KEY, isChecked)
        }
    }

    private var currentToast: Toast? = null

    private fun toast(string: String) {
        val toast = Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT)
        currentToast?.cancel()
        currentToast = toast
        toast.show()
    }

    companion object {
        const val SOURCE_CODE_URL = "http://github.com/beleavemebe/marketplace-app"
    }
}
