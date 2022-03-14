package com.narcissus.marketplace.ui.home.pager.banner

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.databinding.ListItemBannerBinding

data class Banner(val imgUrl: String, val onClick: () -> Unit) {
    companion object {
        @JvmStatic
        private fun inflateBinding(
            inflater: LayoutInflater,
            parent: ViewGroup,
        ) = ListItemBannerBinding.inflate(inflater, parent, false)

        fun delegate() =
            adapterDelegateViewBinding<Banner, Banner, ListItemBannerBinding>(
                ::inflateBinding
            ) {
                bind {
                    binding.ivImage.load(item.imgUrl)
                    binding.root.setOnClickListener { item.onClick() }
                }
            }
    }
}
