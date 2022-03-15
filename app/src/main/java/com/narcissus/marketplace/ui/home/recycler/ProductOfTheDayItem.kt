package com.narcissus.marketplace.ui.home.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemProductOfTheDayBinding
import com.narcissus.marketplace.model.ProductOfTheDay
import com.narcissus.marketplace.ui.home.util.crossOut

data class ProductOfTheDayItem(
    val product: ProductOfTheDay
) {
    companion object {
        @JvmStatic
        private fun inflateBinding(
            inflater: LayoutInflater,
            parent: ViewGroup,
        ) = ListItemProductOfTheDayBinding.inflate(inflater, parent, false)

        fun delegate(onProductClicked: (id: String) -> Unit) =
            adapterDelegateViewBinding<ProductOfTheDayItem, ProductOfTheDayItem, ListItemProductOfTheDayBinding>(
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
                    binding.tvPercentOff.text = context.getString(
                        R.string.percent_off_placeholder, item.product.percentOff,
                    )
                    binding.ivImage.load(item.product.imageUrl)
                    binding.root.setOnClickListener {
                        onProductClicked(item.product.id)
                    }
                }
            }
    }
}
