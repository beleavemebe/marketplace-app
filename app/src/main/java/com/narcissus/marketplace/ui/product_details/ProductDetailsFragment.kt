package com.narcissus.marketplace.ui.product_details

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.ui.home.ExtraHorizontalMarginDecoration
import com.narcissus.marketplace.ui.home.ProductsAdapter
import com.narcissus.marketplace.ui.product_details.reviews.DividerItemDecorator
import com.narcissus.marketplace.ui.product_details.reviews.ReviewsAdapter

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    companion object {
        private const val EXTRA_LEFT_MARGIN = 8
        private const val ROTATION_EXPAND = 0F
        private const val ROTATION_UNEXPAND = 180F
    }
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val reviewsAdapter = ReviewsAdapter()
    private val similarAdapter = ProductsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)
        initTextViews()
        subscribeData()
        initRecyclerViews()
        setOnClickListeners()
        val layoutTransition = binding.root.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun initTextViews() = with(binding) {
        layoutType.aboutTitleTV.text = getString(R.string.type)
        layoutColor.aboutTitleTV.text = getString(R.string.about)
        layoutMaterial.aboutTitleTV.text = getString(R.string.material)
        layoutDescription.aboutTitleTV.text = getString(R.string.description)
    }

    private fun setOnClickListeners() {
        binding.layoutExpandReviewsList.setOnClickListener {
            productDetailsViewModel.changeReviewsListState()
        }
    }

    private fun initRecyclerViews() = with(binding) {
        rvReviews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvReviews.adapter = reviewsAdapter
        rvSimilarProducts.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvReviews.addItemDecoration(
            DividerItemDecorator(
                ContextCompat.getDrawable(requireContext(), R.drawable.recycler_view_divider)!!
            )
        )
        rvSimilarProducts.addItemDecoration(ExtraHorizontalMarginDecoration(EXTRA_LEFT_MARGIN))
        rvSimilarProducts.adapter = similarAdapter

    }

    private fun subscribeData() {
        subscribeMainProductData()
        subscribeReviewsProductData()
        subscribeReviewsExpandState()
    }

    private fun subscribeReviewsExpandState() {
        productDetailsViewModel.isReviewsListExpanded.observe(viewLifecycleOwner) { isExpanded ->
            with(binding) {
                if (isExpanded) {
                    ivExpandReviewsList.rotation = ROTATION_UNEXPAND
                    tvExpandReviewsList.text = getString(R.string.hide_all_reviews)
                } else {
                    ivExpandReviewsList.rotation = ROTATION_EXPAND
                    tvExpandReviewsList.text = getString(R.string.show_all_reviews)
                }
            }
        }
    }

    private fun subscribeMainProductData() {
        productDetailsViewModel.mainProductDataLiveData.observe(viewLifecycleOwner) { data ->
            with(binding) {
                tvProductDepartment.text = data.department
                ivProduct.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.product_img_example
                    )
                )
                tvProductName.text = data.name
                ratingBarProduct.progress = data.rating
                tvPrice.text = getString(R.string.price_placeholder, data.price)
                tvSales.text = getString(R.string.sales_placeholder, data.sales)
                tvStock.text = getString(R.string.in_stock_placeholder, data.stock)
                layoutType.aboutValueTV.text = data.type
                layoutColor.aboutValueTV.text = data.color
                layoutMaterial.aboutValueTV.text = data.material
                layoutDescription.aboutValueTV.text = data.description
            }
        }
    }

    private fun subscribeReviewsProductData() {
        productDetailsViewModel.reviewsProductData.observe(viewLifecycleOwner) {
            reviewsAdapter.submitItems(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
