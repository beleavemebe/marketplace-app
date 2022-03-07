package com.narcissus.marketplace.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemProductPreviewBinding
import com.narcissus.marketplace.model.ProductPreview

class ProductViewHolder(
    private val binding: ListItemProductPreviewBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(productPreview: ProductPreview) = with(binding) {
        productNameTextView.text = productPreview.name
        productImageView.setImageResource(R.drawable.product_img_example) // TODO: replace with Coil call
        productRatingBar.progress = productPreview.rating * 2
        productPriceTextView.text = itemView.context.getString(R.string.price_placeholder, productPreview.price)
        productSalesTextView.text = itemView.context.getString(R.string.sales_placeholder, productPreview.sales)
        productStockTextView.text = itemView.context.getString(R.string.in_stock_placeholder, productPreview.stock)
    }
}