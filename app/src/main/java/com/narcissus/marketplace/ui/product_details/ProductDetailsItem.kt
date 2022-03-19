package com.narcissus.marketplace.ui.product_details

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.card.MaterialCardView
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.ListItemDetailsDividerBinding
import com.narcissus.marketplace.databinding.ListItemDetailsMainInfoBinding
import com.narcissus.marketplace.databinding.ListItemDetailsMainInfoPlaceholderBinding
import com.narcissus.marketplace.databinding.ListItemDetailsPriceBinding
import com.narcissus.marketplace.databinding.ListItemDetailsProductAboutMultipleLinesBinding
import com.narcissus.marketplace.databinding.ListItemDetailsProductAboutSingleLineBinding
import com.narcissus.marketplace.databinding.ListItemDetailsProductPlaceholderBinding
import com.narcissus.marketplace.databinding.ListItemDetailsReviewPreviewBinding
import com.narcissus.marketplace.databinding.ListItemDetailsTitileBasicBinding
import com.narcissus.marketplace.databinding.ListItemDetailsTitleButtonBinding
import com.narcissus.marketplace.databinding.ListItemRecyclerBinding
import com.narcissus.marketplace.domain.model.Review
import com.narcissus.marketplace.ui.home.recycler.ExtraHorizontalMarginDecoration
import com.narcissus.marketplace.ui.product_details.main_info_recycler_view.ProductMainInfoAdapter
import com.narcissus.marketplace.ui.product_details.main_info_recycler_view.ProductMainInfoItem
import com.narcissus.marketplace.ui.product_details.similar.SimilarProductListItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

typealias ProductPriceBinding = ListItemDetailsPriceBinding
typealias ProductMainInfoBinding = ListItemDetailsMainInfoBinding
typealias LoadingMainProductInfoBinding = ListItemDetailsMainInfoPlaceholderBinding
typealias ProductBasicTitleBinding = ListItemDetailsTitileBasicBinding
typealias ProductButtonTitleBinding = ListItemDetailsTitleButtonBinding
typealias AboutSingleLineBinding = ListItemDetailsProductAboutSingleLineBinding
typealias AboutMultipleLineBinding = ListItemDetailsProductAboutMultipleLinesBinding
typealias LoadingProductDetailsBinding = ListItemDetailsProductPlaceholderBinding
typealias ReviewsPreviewBinding = ListItemDetailsReviewPreviewBinding
typealias SimilarProductsListBinding = ListItemRecyclerBinding
typealias DividerBinding = ListItemDetailsDividerBinding

sealed class ProductDetailsItem {
    data class Price(val price: Int) : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = ProductPriceBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<Price, ProductDetailsItem, ProductPriceBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvDetailsPrice.text =
                            context.getString(R.string.price_placeholder, item.price)
                    }
                }
        }
    }

    data class ProductMainInfo(
        val infoItems: List<ProductMainInfoItem>,
    ) : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = ProductMainInfoBinding.inflate(layoutInflater, root, false)

            fun delegate(purchaseButtonListener: () -> Unit, goToCartButtonListener: () -> Unit) =
                adapterDelegateViewBinding<ProductMainInfo, ProductDetailsItem, ProductMainInfoBinding>(
                    ::inflateBinding,
                ) {
                    val adapter = ProductMainInfoAdapter(
                        purchaseButtonListener,
                        goToCartButtonListener,
                    )
                    binding.rvMainInfo.layoutManager = GridLayoutManager(context, 2)
                    binding.rvMainInfo.adapter = adapter
                    binding.rvMainInfo.isNestedScrollingEnabled = false
                    bind {
                        adapter.items = item.infoItems
                    }
                }
        }
    }

    class LoadingMainProductInfo : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = LoadingMainProductInfoBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<LoadingMainProductInfo, ProductDetailsItem, LoadingMainProductInfoBinding>(
                    ::inflateBinding,
                ) {
                }
        }
    }

    data class BasicTitle(@StringRes val titleId: Int) : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = ProductBasicTitleBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<BasicTitle, ProductDetailsItem, ProductBasicTitleBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvDetailsTitle.text = context.getString(item.titleId)
                    }
                }
        }
    }

    class LoadingProductDetails : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = LoadingProductDetailsBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<LoadingProductDetails, ProductDetailsItem, LoadingProductDetailsBinding>(
                    ::inflateBinding,
                ) {
                }
        }
    }

    data class AboutSingleLine(@StringRes val titleId: Int, val value: String) :
        ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = AboutSingleLineBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<AboutSingleLine, ProductDetailsItem, AboutSingleLineBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvDetailsAboutTitleSingleLine.text =
                            context.getString(item.titleId)
                        binding.tvDetailsAboutValueSingleLine.text = item.value
                    }
                }
        }
    }

    data class AboutMultipleLine(@StringRes val titleId: Int, val value: String) :
        ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = AboutMultipleLineBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<AboutMultipleLine, ProductDetailsItem, AboutMultipleLineBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvDetailsAboutTitleMultipleLine.text =
                            context.getString(item.titleId)
                        binding.tvDetailsAboutValueMultipleLine.text = item.value
                    }
                }
        }
    }

    data class ButtonTitle(
        @StringRes val titleId: Int,
        @StringRes val buttonNameId: Int,
    ) : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = ProductButtonTitleBinding.inflate(layoutInflater, root, false)

            fun delegate(onButtonClicked: () -> Unit) =
                adapterDelegateViewBinding<ButtonTitle, ProductDetailsItem, ProductButtonTitleBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvDetailsButtonTitle.text = context.getString(item.titleId)
                        binding.tvBtnDetailsTitle.text = context.getString(item.buttonNameId)
                        binding.tvBtnDetailsTitle.setOnClickListener { onButtonClicked() }
                    }
                }
        }
    }

    data class ReviewsPreview(val review: Review) : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = ReviewsPreviewBinding.inflate(layoutInflater, root, false)

            fun delegate(
                onReviewClicked: () -> Unit,
                isExpanded: Flow<Boolean>,
                scope: CoroutineScope,
            ) =
                adapterDelegateViewBinding<ReviewsPreview, ProductDetailsItem, ReviewsPreviewBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.ivReviewPreviewAvatar.load(item.review.reviewAuthorIcon) {
                            transformations(CircleCropTransformation())
                        }

                        binding.tvReviewPreviewDescription.setOnClickListener { onReviewClicked() }
                        binding.reviewPreviewRatingBar.progress = item.review.rating
                        binding.tvReviewPreviewAuthor.text = item.review.author
                        binding.tvReviewPreviewDescription.text = item.review.details

                        isExpanded.onEach { isExpanded ->
                            if (isExpanded) {
                                binding.tvReviewPreviewDescription
                                    .animateMaxLines(REVIEW_EXPANDED_MAX_LINES)
                            } else {
                                binding.tvReviewPreviewDescription
                                    .animateMaxLines(REVIEW_COLLAPSED_MAX_LINES)
                            }
                        }.launchIn(scope)
                    }
                }

            private fun TextView.animateMaxLines(linesCount: Int) {
                ObjectAnimator.ofInt(
                    this,
                    MAX_LINES_PROPERTY_NAME,
                    linesCount,
                ).apply {
                    duration = ANIM_DURATION
                    interpolator = FastOutSlowInInterpolator()
                }.start()
            }
        }
    }

    data class SimilarProducts(
        val similarProducts: List<SimilarProductListItem>,
    ) : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = SimilarProductsListBinding.inflate(layoutInflater, root, false)

            fun delegate(
                onClicked: (productId: String, cardView: MaterialCardView) -> Unit,
                onAddToCartClicked: (productId: String) -> Unit,
            ) =
                adapterDelegateViewBinding<SimilarProducts, ProductDetailsItem, SimilarProductsListBinding>(
                    ::inflateBinding,
                ) {
                    val adapter = AsyncListDifferDelegationAdapter(
                        SimilarProductListItem.DIFF_CALLBACK,
                        SimilarProductListItem.SimilarProductItem.delegate(
                            onClicked,
                            onAddToCartClicked,
                        ),
                    )
                    binding.root.addItemDecoration(ExtraHorizontalMarginDecoration(EXTRA_LEFT_MARGIN))
                    binding.root.adapter = adapter

                    bind {
                        adapter.items = item.similarProducts
                    }
                }
        }
    }

    class Divider : ProductDetailsItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = DividerBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<Divider, ProductDetailsItem, DividerBinding>(
                    ::inflateBinding,
                ) {
                }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductDetailsItem>() {
            override fun areItemsTheSame(
                oldItem: ProductDetailsItem,
                newItem: ProductDetailsItem,
            ): Boolean {
                return when (oldItem) {
                    is Price -> newItem is Price && oldItem.price == newItem.price
                    is BasicTitle -> newItem is BasicTitle && oldItem.titleId == newItem.titleId
                    is AboutSingleLine -> newItem is AboutSingleLine && oldItem.value == newItem.value
                    is AboutMultipleLine -> newItem is AboutMultipleLine && oldItem.value == newItem.value
                    is ButtonTitle -> newItem is ButtonTitle && oldItem.titleId == newItem.titleId
                    is ReviewsPreview -> newItem is ReviewsPreview && oldItem.review.reviewId == newItem.review.reviewId
                    is SimilarProducts -> newItem is SimilarProducts && oldItem.similarProducts === newItem.similarProducts
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: ProductDetailsItem,
                newItem: ProductDetailsItem,
            ): Boolean {
                return oldItem == newItem
            }
        }

        private const val REVIEW_EXPANDED_MAX_LINES = 24
        private const val REVIEW_COLLAPSED_MAX_LINES = 4
        private const val ANIM_DURATION = 200L
        private const val MAX_LINES_PROPERTY_NAME = "maxLines"
        private const val EXTRA_LEFT_MARGIN = 8
    }
}
