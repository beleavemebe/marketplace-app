package com.narcissus.marketplace.ui.user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentUserBinding
import com.narcissus.marketplace.ui.user.theme.DefaultTheme
import com.narcissus.marketplace.ui.user.theme.HeaderBackgroundColor
import com.narcissus.marketplace.ui.user.theme.Montserrat
import com.narcissus.marketplace.ui.user.theme.MontserratMedium
import com.narcissus.marketplace.ui.user.theme.MontserratSemiBold

class UserFragment : Fragment(R.layout.fragment_user) {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserBinding.bind(view)
        with(binding.root) {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                DefaultTheme {
                    Column(
                        modifier = Modifier
                            .background(White)
                    ) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Profile",
                                    style = TextStyle(
                                        color = Black,
                                        fontSize = 22.sp,
                                        fontFamily = Montserrat,
                                    )
                                )
                            },
                            backgroundColor = White,
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
                                iconResId = R.drawable.ic_cart,
                                onClick = { toast("Billing") }
                            )

                            Item(
                                text = "Logout",
                                iconResId = R.drawable.ic_cart,
                                onClick = { toast("Logout") }
                            )

                            Header(text = "Application")

                            // TODO: track whether app is in dark theme or not
                            val isSystemInDarkTheme = false
                            SwitchItem(
                                text = "Dark Theme",
                                iconResId = R.drawable.ic_cart,
                                checked = isSystemInDarkTheme
                            ) { flag ->
                                toast("Dark Theme: $flag")
                            }

                            Item(
                                text = "Clear data",
                                iconResId = R.drawable.ic_cart,
                                onClick = { toast("Clear data") }
                            )

                            Item(
                                text = "Report bug",
                                iconResId = R.drawable.ic_cart,
                                onClick = {
                                    toast("Report bug")
                                }
                            )

                            Item(
                                text = "Source code",
                                iconResId = R.drawable.ic_cart,
                                onClick = { toast("Source code") }
                            )
                        }
                    }
                }
            }
        }
    }

    private var currentToast: Toast? = null

    private fun toast(string: String) {
        val toast = Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT)
        currentToast?.cancel()
        currentToast = toast
        toast.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

@Composable
fun ProfileInfo(name: String, email: String) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            contentScale = ContentScale.Crop,
            painter = painterResource(R.drawable.profile_avatar_placeholder_large),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            fontFamily = MontserratSemiBold,
            text = name,
            style = TextStyle(
                fontSize = 18.sp
            ),
        )

        Text(
            fontFamily = Montserrat,
            text = email,
            style = TextStyle(
                color = Color(0xFF6B6B6B),
                fontSize = 16.sp
            ),
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview
@Composable
fun ProfileInfoPreview() {
    ProfileInfo(name = "Joe Ordinary", email = "example@gmail.com")
}

@Composable
fun Header(text: String) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .background(HeaderBackgroundColor),
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontFamily = MontserratMedium,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
    }
}

@Preview
@Composable
fun HeaderPreview() {
    Column {
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
            .height(40.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(CornerSize(8.dp)))
            .background(White)
            .clickable { onClick() }
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        Image(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = TextStyle(
                fontFamily = Montserrat,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.fillMaxWidth(0.8f))

        appendContent()
    }
}

@Composable
@Preview
fun ItemPreview() {
    Item("Orders", R.drawable.ic_catalog) {}
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

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
@Preview
fun SwitchItemPreview() {
    SwitchItem("Orders", R.drawable.ic_catalog, true) {}
}
