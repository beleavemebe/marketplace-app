package com.narcissus.marketplace.core.navigation

import android.net.Uri

fun interface NavDestination {
    fun url(): String
}

val NavDestination.uri: Uri
    get() = Uri.parse(url())
