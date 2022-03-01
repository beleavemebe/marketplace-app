package com.narcissus.marketplace.ui.product_details.about

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListItemDetailsProductsAboutMultipleLinesBinding
import com.narcissus.marketplace.databinding.ListItemDetailsProductsAboutSingleLineBinding


typealias AboutSingleLineBinding = ListItemDetailsProductsAboutSingleLineBinding
typealias AboutMultipleLineBinding = ListItemDetailsProductsAboutMultipleLinesBinding

sealed class AboutItem {

    data class SingleLineItem(val title: String, val value: String) : AboutItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = AboutSingleLineBinding.inflate(layoutInflater, root, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<SingleLineItem, AboutItem, AboutSingleLineBinding>(
                        ::inflateBinding
                    ) {
                        bind {
                            binding.tvAboutTitleSingleLine.text = item.title
                            binding.tvAboutValueSingleLine.text = item.value
                        }
                    }
        }
    }

    data class MultipleLineItem(val title: String, val value: String) : AboutItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = AboutMultipleLineBinding.inflate(layoutInflater, root, false)

            val delegate
                    get() =
                adapterDelegateViewBinding<MultipleLineItem, AboutItem, AboutMultipleLineBinding>(
                    ::inflateBinding
                ) {
                    bind {
                        binding.tvAboutTitleMultipleLine.text = item.title
                        binding.tvAboutValueMultipleLine.text = item.value
                    }
                }
        }
    }

}
