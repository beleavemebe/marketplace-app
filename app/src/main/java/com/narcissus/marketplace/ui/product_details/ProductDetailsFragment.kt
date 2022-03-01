package com.narcissus.marketplace.ui.product_details

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.model.DetailsAbout
import com.narcissus.marketplace.ui.home.recycler.ExtraHorizontalMarginDecoration
import com.narcissus.marketplace.ui.product_details.about.AboutItem
import com.narcissus.marketplace.ui.product_details.reviews.DividerItemDecorator
import com.narcissus.marketplace.ui.product_details.reviews.ReviewsAdapter
import com.narcissus.marketplace.ui.products.ProductsAdapter
import kotlinx.coroutines.launch

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    companion object {
        private const val EXTRA_LEFT_MARGIN = 8
        private const val ROTATION_EXPAND = 0F
        private const val ROTATION_UNEXPAND = 180F
    }

    private val viewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val reviewsAdapter = ReviewsAdapter()
    private val similarAdapter = ProductsAdapter {}


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)
        initTextViews()
        subscribeViewModel()
        initReviewsRecyclerView()
        initSimilarProductsRecyclerView()
        setOnClickListeners()
        val layoutTransition = binding.root.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }


    private fun initReviewsRecyclerView() = with(binding.rvReviews) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = reviewsAdapter
        addItemDecoration(
            DividerItemDecorator(
                ContextCompat.getDrawable(requireContext(), R.drawable.recycler_view_divider)!!
            )
        )
    }

    private fun initSimilarProductsRecyclerView() = with(binding.rvSimilarProducts){
        layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        addItemDecoration(ExtraHorizontalMarginDecoration(EXTRA_LEFT_MARGIN))
        adapter = similarAdapter
    }

    private fun initTextViews() = with(binding) {
        layoutType.tvAboutTitleSingleLine.text = getString(R.string.type)
        layoutColor.tvAboutTitleSingleLine.text = getString(R.string.about)
        layoutMaterial.tvAboutTitleSingleLine.text = getString(R.string.material)
        layoutDescription.tvAboutTitleMultipleLine.text = getString(R.string.description)
    }

    private fun setOnClickListeners() {
        binding.layoutExpandReviewsList.setOnClickListener {
            viewModel.changeReviewsListState()
        }
    }

    private fun subscribeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            subscribeMainProductData()
            launch {
                subscribeReviews()
            }
            launch {
                subscribeReviewsExpandState()
            }
        }

    }

    private suspend fun subscribeReviewsExpandState() {
        viewModel.isReviewsListExpandedFlow.collect { isExpanded ->
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

    private suspend fun subscribeMainProductData() {
        viewModel.productDetailsFlow.collect() { data ->
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
                layoutType.tvAboutValueSingleLine.text =
                    (data.aboutList[0] as DetailsAbout.Type).data
                layoutColor.tvAboutValueSingleLine.text =
                    (data.aboutList[1] as DetailsAbout.Color).data
                layoutMaterial.tvAboutValueSingleLine.text =
                    (data.aboutList[2] as DetailsAbout.Material).data
                layoutDescription.tvAboutValueMultipleLine.text =
                    (data.aboutList[3] as DetailsAbout.Description).data
                similarAdapter.submitItems(data.similarProducts)
            }
        }
    }

    private suspend fun subscribeReviews() {
        viewModel.productReviewsFlow.collect {
            reviewsAdapter.submitItems(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
