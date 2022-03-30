package com.narcissus.marketplace.core.navigation.destination

import android.net.Uri

sealed interface NavDestination {
    val url: String
}

val NavDestination.uri: Uri
    get() = Uri.parse(url)
