package com.narcissus.marketplace.ui.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.narcissus.marketplace.core.R
import com.narcissus.marketplace.core.navigation.destination.SignInDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.Constants
import com.narcissus.marketplace.domain.model.UserProfile
import com.narcissus.marketplace.ui.user.theme.DarkTheme
import com.narcissus.marketplace.ui.user.theme.DefaultPadding
import com.narcissus.marketplace.ui.user.theme.DefaultTheme
import com.narcissus.marketplace.ui.user.theme.HalfPadding
import com.narcissus.marketplace.ui.user.theme.HeaderHeight
import com.narcissus.marketplace.ui.user.theme.IconSize
import com.narcissus.marketplace.ui.user.theme.IntermediatePadding
import com.narcissus.marketplace.ui.user.theme.ItemHeight
import com.narcissus.marketplace.ui.user.theme.LightTheme
import com.narcissus.marketplace.ui.user.theme.Montserrat
import com.narcissus.marketplace.ui.user.theme.SmallPadding
import com.narcissus.marketplace.ui.user.theme.regular
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
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
                    onSignOutClicked = viewModel::onSignOutClicked,
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
        }
    }

    private fun navigateToSignIn() {
        val destination: SignInDestination by inject { parametersOf(true) }
        navigator.navigate(destination)
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
}
