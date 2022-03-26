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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import com.narcissus.marketplace.domain.model.dummyUser
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
                UserScreen(viewModel)
            }
        }
    }

    @Composable
    private fun UserScreen(viewModel: UserViewModel) {
        viewModel.collectSideEffect(::handleSideEffect)
        val state = viewModel.collectAsState().value
        when {
            state.isLoading -> Loading()
            state.isUserAuthenticated == false -> YouAreNotLoggedIn(viewModel::onSignInClicked)
            state.user != null ->
                UserScreenContent(
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

@Composable
fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
fun LoadingPreviewLight() {
    LightTheme {
        Loading()
    }
}

@Composable
@Preview
fun LoadingPreviewDark() {
    DarkTheme {
        Loading()
    }
}

@Composable
fun YouAreNotLoggedIn(onSignInClicked: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        Text(
            text = "You are not logged in",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(
                id = R.drawable.profile_avatar_placeholder_large,
            ),
            contentDescription = "Not logged in image",
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onSignInClicked) {
            Text("Sign In")
        }
    }
}

@Composable
@Preview
fun YouAreNotLoggedInPreviewLight() {
    LightTheme {
        YouAreNotLoggedIn {}
    }
}

@Composable
@Preview
fun YouAreNotLoggedInPreviewDark() {
    DarkTheme {
        YouAreNotLoggedIn {}
    }
}


@Composable
fun UserScreenContent(
    viewModel: UserViewModel,
    userProfile: UserProfile,
    isAppInDarkTheme: Boolean,
    onSignOutClicked: () -> Unit,
) {
    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Profile",
                    style = MaterialTheme.typography.h5.regular,
                )
            },
            backgroundColor = MaterialTheme.colors.surface,
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            ProfileInfo(userProfile)

            Header(text = "My Profile")

            Item(
                text = "Orders",
                iconResId = R.drawable.ic_cart,
            ) {
                viewModel.toast("Orders")
            }

            Item(
                text = "Billing",
                iconResId = R.drawable.ic_card,
            ) {
                viewModel.toast("Billing")
            }

            Item(
                text = "Sign out",
                iconResId = R.drawable.ic_sign_out,
            ) {
                onSignOutClicked()
            }

            Header(text = "Application")

            SwitchItem(
                text = "Dark Theme",
                iconResId = R.drawable.ic_crescent,
                checked = isAppInDarkTheme,
            ) { checked ->
                viewModel.switchTheme(checked)
            }

            Item(
                text = "Clear data",
                iconResId = R.drawable.ic_broom,
            ) {
                viewModel.toast("Clear data")
            }

            Item(
                text = "Report bug",
                iconResId = R.drawable.ic_bug,
            ) {
                viewModel.toast("Report bug")
            }

            Item(
                text = "Source code",
                iconResId = R.drawable.ic_code,
            ) {
                viewModel.toast("Source code")
            }
        }
    }
}

@Composable
fun ProfileInfo(userProfile: UserProfile) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(userProfile.iconUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.profile_avatar_placeholder_large),
            contentScale = ContentScale.Crop,
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
        )

        Spacer(modifier = Modifier.height(DefaultPadding))

        Text(
            text = userProfile.name ?: "nullable? wtf???",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
        )

        Text(
            fontFamily = Montserrat,
            text = userProfile.email,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondaryVariant,
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun ProfileInfoPreviewLight() {
    LightTheme {
        ProfileInfo(dummyUser)
    }
}

@Preview
@Composable
fun ProfileInfoPreviewDark() {
    DarkTheme {
        ProfileInfo(dummyUser)
    }
}

@Composable
fun Header(text: String) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(HeaderHeight)
            .padding(horizontal = DefaultPadding, vertical = HalfPadding)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colors.onSecondary),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(horizontal = HalfPadding),
        )
    }
}

@Preview
@Composable
fun HeaderPreview() {
    DefaultTheme {
        Header(text = "My Profile")
    }
}

@Composable
fun Item(
    text: String,
    @DrawableRes iconResId: Int,
    tailContent: @Composable () -> Unit = {},
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(ItemHeight)
            .padding(horizontal = DefaultPadding)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() },
    ) {
        Spacer(modifier = Modifier.width(HalfPadding))

        Image(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
            modifier = Modifier.size(IconSize),
        )

        Spacer(modifier = Modifier.width(IntermediatePadding))

        Text(
            text = text,
            style = MaterialTheme.typography.body1.regular,
            modifier = Modifier.padding(vertical = SmallPadding),
            color = MaterialTheme.colors.onPrimary,
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.8f))

        tailContent()
    }
}

@Composable
@Preview
fun ItemPreviewLight() {
    LightTheme {
        Item("Orders", R.drawable.ic_cart) {}
    }
}

@Composable
@Preview
fun ItemPreviewDark() {
    DarkTheme {
        Item("Orders", R.drawable.ic_cart) {}
    }
}

@Composable
fun SwitchItem(
    text: String,
    @DrawableRes iconResId: Int,
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
) {
    Item(
        text = text,
        iconResId = iconResId,
        tailContent = {
            var isChecked by remember { mutableStateOf(checked) }
            Switch(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = !isChecked
                    onChecked(isChecked)
                },
            )

            Spacer(modifier = Modifier.width(DefaultPadding))
        },
    ) {}
}

@Composable
@Preview
fun SwitchItemPreviewLight() {
    LightTheme {
        SwitchItem("Orders", R.drawable.ic_cart, true) {}
    }
}


@Composable
@Preview
fun SwitchItemPreviewDark() {
    DarkTheme {
        SwitchItem("Orders", R.drawable.ic_cart, true) {}
    }
}
