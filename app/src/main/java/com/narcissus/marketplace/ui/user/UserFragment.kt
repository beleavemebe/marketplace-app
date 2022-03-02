package com.narcissus.marketplace.ui.user

import android.os.Bundle
import android.view.View
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
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
                            .verticalScroll(rememberScrollState())
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

                        ProfileInfo()
                        Spacer(modifier = Modifier.height(32.dp))
                        Header(text = "My Profile")
                        Item("Orders", R.drawable.ic_cart)
                        Item("Billing", R.drawable.ic_cart)
                        Item("Logout", R.drawable.ic_cart)
                        Header(text = "Application")
                        Item("Dark Theme", R.drawable.ic_cart)
                        Item("Clear data", R.drawable.ic_cart)
                        Item("Report bug", R.drawable.ic_cart)
                        Item("Source code", R.drawable.ic_cart)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

@Preview
@Composable
fun ProfileInfo() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
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
            text = "Joe Ordinary",
            style = TextStyle(
                fontSize = 18.sp
            ),
        )

        Text(
            fontFamily = Montserrat,
            text = "example@gmail.com",
            style = TextStyle(
                color = Color(0xFF6B6B6B),
                fontSize = 16.sp
            ),
        )
    }
}

@Composable
fun Header(text: String) {
    Row {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(CornerSize(8.dp)))
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .background(HeaderBackgroundColor)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    fontFamily = MontserratMedium,
                    fontSize = 18.sp
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
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
fun Item(text: String, @DrawableRes iconResId: Int? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(White)
    ) {
        Spacer(modifier = Modifier.width(8.dp))

        if (iconResId != null) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = text,
                Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))
        }

        Text(
            text = text,
            style = TextStyle(
                fontFamily = Montserrat,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
@Preview
fun ItemPreview() {
    Column {
        Item("Orders")
        Item("Orders", R.drawable.ic_catalog)
    }
}
