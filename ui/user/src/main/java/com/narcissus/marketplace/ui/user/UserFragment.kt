package com.narcissus.marketplace.ui.user

import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.narcissus.marketplace.core.R
import com.narcissus.marketplace.core.util.Constants
import com.narcissus.marketplace.ui.user.theme.DefaultPadding
import com.narcissus.marketplace.ui.user.theme.DefaultTheme
import com.narcissus.marketplace.ui.user.theme.HalfPadding
import com.narcissus.marketplace.ui.user.theme.HeaderHeight
import com.narcissus.marketplace.ui.user.theme.IconSize
import com.narcissus.marketplace.ui.user.theme.IntermediatePadding
import com.narcissus.marketplace.ui.user.theme.ItemHeight
import com.narcissus.marketplace.ui.user.theme.Montserrat
import com.narcissus.marketplace.ui.user.theme.SmallPadding

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            DefaultTheme {
                Column {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Profile",
                                style = MaterialTheme.typography.h4
                            )
                        },
                        backgroundColor = MaterialTheme.colors.surface
                    )

                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        ProfileInfo(name = "Joe Ordinary", email = "example@gmail.com")

                        Header(text = "My Profile")

                        Item(
                            text = "Orders",
                            iconResId = R.drawable.ic_cart,
                            onClick = { toast("Orders") }
                        )

                        Item(
                            text = "Billing",
                            iconResId = R.drawable.ic_card,
                            onClick = { toast("Billing") }
                        )

                        Item(
                            text = "Logout",
                            iconResId = R.drawable.ic_logout,
                            onClick = { toast("Logout") }
                        )

                        Header(text = "Application")

                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                        val isSystemInDarkTheme = sharedPref?.getBoolean(Constants.THEME_KEY, false)
                        SwitchItem(
                            text = "Dark Theme",
                            iconResId = R.drawable.ic_crescent,
                            checked = isSystemInDarkTheme!!,
                        ) { checked ->
                            switchTheme(checked, sharedPref)
                        }

                        Item(
                            text = "Clear data",
                            iconResId = R.drawable.ic_broom,
                            onClick = { toast("Clear data") }
                        )

                        Item(
                            text = "Report bug",
                            iconResId = R.drawable.ic_bug,
                            onClick = {
                                toast("Report bug")
                            }
                        )

                        Item(
                            text = "Source code",
                            iconResId = R.drawable.ic_code,
                            onClick = { toast("Source code") },
                        )
                    }
                }
            }
        }
    }

    private fun switchTheme(isChecked: Boolean, sharedPref: SharedPreferences) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            sharedPref.edit()?.putBoolean(Constants.THEME_KEY, true)?.apply()
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            sharedPref.edit()?.putBoolean(Constants.THEME_KEY, false)?.apply()
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
fun ProfileInfo(name: String, email: String) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(DefaultPadding))

        Image(
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.profile_avatar_placeholder_large),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(DefaultPadding))

        Text(
            text = name,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.onPrimary
        )

        Text(
            fontFamily = Montserrat,
            text = email,
            style = MaterialTheme.typography.subtitle1.copy(
                color = MaterialTheme.colors.secondaryVariant
            )
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun ProfileInfoPreview() {
    DefaultTheme {
        ProfileInfo(name = "Joe Ordinary", email = "example@gmail.com")
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
            .background(MaterialTheme.colors.onSecondary)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onPrimary,
            modifier = Modifier.padding(horizontal = HalfPadding)
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
    onClick: () -> Unit = {},
    appendContent: @Composable () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(ItemHeight)
            .padding(horizontal = DefaultPadding)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
    ) {
        Spacer(modifier = Modifier.width(HalfPadding))

        Image(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
            modifier = Modifier.size(IconSize)
        )

        Spacer(modifier = Modifier.width(IntermediatePadding))

        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(vertical = SmallPadding),
            color = MaterialTheme.colors.onPrimary
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.8f))

        appendContent()
    }
}

@Composable
@Preview
fun ItemPreview() {
    DefaultTheme {
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
        onClick = {}
    ) {
        var isChecked by remember { mutableStateOf(checked) }
        Switch(
            checked = isChecked,
            onCheckedChange = {
                isChecked = !isChecked
                onChecked(isChecked)
            }
        )

        Spacer(modifier = Modifier.width(DefaultPadding))
    }
}

@Composable
@Preview
fun SwitchItemPreview() {
    DefaultTheme {
        SwitchItem("Orders", R.drawable.ic_cart, true) {}
    }
}
