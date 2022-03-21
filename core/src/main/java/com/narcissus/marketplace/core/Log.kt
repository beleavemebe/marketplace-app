package com.narcissus.marketplace.core

import android.util.Log

@Suppress("unused")
inline fun Any.log(msg: () -> Any?) = Log.d("marketplace-debug", msg().toString())
