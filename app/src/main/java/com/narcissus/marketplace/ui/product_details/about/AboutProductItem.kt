package com.narcissus.marketplace.ui.product_details.about

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListItemDetailsProductAboutMultipleLinesBinding
import com.narcissus.marketplace.databinding.ListItemDetailsProductAboutSingleLineBinding

typealias AboutSingleLineBinding = ListItemDetailsProductAboutSingleLineBinding
typealias AboutMultipleLineBinding = ListItemDetailsProductAboutMultipleLinesBinding

sealed class AboutProductItem {

    data class SingleLineItem(val title: String, val value: String) : AboutProductItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = AboutSingleLineBinding.inflate(layoutInflater, root, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<SingleLineItem, AboutProductItem, AboutSingleLineBinding>(
                        ::inflateBinding
                    ) {
                        bind {
                            binding.tvAboutTitleSingleLine.text = item.title
                            binding.tvAboutValueSingleLine.text = item.value
                        }
                    }
        }
    }

    data class MultipleLineItem(val title: String, val value: String) : AboutProductItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup
            ) = AboutMultipleLineBinding.inflate(layoutInflater, root, false)

            val delegate
                get() =
                    adapterDelegateViewBinding<MultipleLineItem, AboutProductItem, AboutMultipleLineBinding>(
                        ::inflateBinding
                    ) {
                        bind {
                            binding.tvAboutTitleMultipleLine.text = item.title
                            binding.tvAboutValueMultipleLine.text = item.value
                        }
                    }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AboutProductItem>() {
            override fun areItemsTheSame(
                oldItem: AboutProductItem,
                newItem: AboutProductItem
            ): Boolean {
                return when (oldItem) {
                    is SingleLineItem -> newItem is SingleLineItem && oldItem.title == newItem.title
                    is MultipleLineItem -> newItem is MultipleLineItem && oldItem.title == newItem.title
                }
            }

            override fun areContentsTheSame(
                oldItem: AboutProductItem,
                newItem: AboutProductItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}
