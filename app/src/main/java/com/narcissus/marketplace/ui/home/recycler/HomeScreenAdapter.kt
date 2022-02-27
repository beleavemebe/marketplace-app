package com.narcissus.marketplace.ui.home.recycler

import androidx.recyclerview.widget.AsyncDifferConfig
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import java.util.concurrent.Executors

class HomeScreenAdapter : AsyncListDifferDelegationAdapter<HomeScreenItem>(
    DIFFER_CONFIG,
    HomeScreenItem.Header.delegate,
    HomeScreenItem.ProductList.delegate,
    HomeScreenItem.LoadingProductList.delegate,
) {
    companion object {
        @JvmStatic
        private val DIFFER_THREAD = Executors.newSingleThreadExecutor()

        // Default config seems to run multiple diff checks in parallel,
        // which results in race condition and loss of some of the content changes
        // That's why we have to run differ on our own executor
        val DIFFER_CONFIG = AsyncDifferConfig.Builder(HomeScreenItem.DIFF_CALLBACK)
            .setBackgroundThreadExecutor(DIFFER_THREAD)
            .build()
    }
}
