package com.narcissus.marketplace.ui.products

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemProductPreviewBinding
import com.narcissus.marketplace.model.ProductPreview

class ProductViewHolder(
    private val binding: ListItemProductPreviewBinding,
    private val onProductClicked: (id: String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(productPreview: ProductPreview) = with(binding) {
        productNameTextView.text = productPreview.name
        productImageView.load(productPreview.icon)
        productRatingBar.progress = productPreview.rating * 2
        productPriceTextView.text = itemView.context.getString(R.string.price_placeholder, productPreview.price)
        productSalesTextView.text = itemView.context.getString(R.string.sales_placeholder, productPreview.sales)
        productStockTextView.text = itemView.context.getString(R.string.in_stock_placeholder, productPreview.stock)
        root.setOnClickListener {
            onProductClicked(productPreview.id)
        }
    }
}
