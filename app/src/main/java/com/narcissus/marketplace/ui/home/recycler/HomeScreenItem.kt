package com.narcissus.marketplace.ui.home.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemBannerBinding
import com.narcissus.marketplace.databinding.ListItemHeadlineBinding
import com.narcissus.marketplace.databinding.ListItemProductOfTheDayBinding
import com.narcissus.marketplace.ui.home.pager.banner.Banner
import com.narcissus.marketplace.ui.home.util.crossOut

typealias HeadlineBinding = ListItemHeadlineBinding
typealias BannerBinding = ListItemBannerBinding
typealias ProductOfTheDayBinding = ListItemProductOfTheDayBinding

sealed class HomeScreenItem {
    data class Headline(@StringRes val titleResId: Int) : HomeScreenItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = ListItemHeadlineBinding.inflate(inflater, parent, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<Headline, HomeScreenItem, HeadlineBinding>(
                        ::inflateBinding,
                    ) {
                        bind {
                            binding.tvDetailsTitle.text = context
                                .getString(item.titleResId)
                        }
                    }
        }
    }

    data class BannerItem(val banner: Banner) : HomeScreenItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = ListItemBannerBinding.inflate(inflater, parent, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<BannerItem, HomeScreenItem, BannerBinding>(
                        ::inflateBinding,
                    ) {
                        bind {

                        }
                    }
        }
    }

    data class ProductOfTheDayItem(val product: ProductOfTheDay) : HomeScreenItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = ListItemProductOfTheDayBinding.inflate(inflater, parent, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<ProductOfTheDayItem, HomeScreenItem, ProductOfTheDayBinding>(
                        ::inflateBinding,
                    ) {
                        bind {
                            binding.tvProductTitle.text = item.product.name
                            binding.tvOldPrice.text = context.getString(
                                R.string.price_placeholder, item.product.oldPrice,
                            )
                            binding.tvOldPrice.crossOut()
                            binding.tvNewPrice.text = context.getString(
                                R.string.price_placeholder, item.product.newPrice,
                            )
                            binding.ivImage.load(item.product.imageUrl)
                        }
                    }
        }
    }
}
