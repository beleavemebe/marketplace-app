package com.narcissus.marketplace.ui.product_details

import android.animation.LayoutTransition
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.model.DetailsAbout
import com.narcissus.marketplace.ui.home.recycler.ExtraHorizontalMarginDecoration
import com.narcissus.marketplace.ui.product_details.about.AboutProductAdapter
import com.narcissus.marketplace.ui.product_details.about.AboutProductItem
import com.narcissus.marketplace.ui.product_details.reviews.DividerItemDecorator
import com.narcissus.marketplace.ui.product_details.reviews.ReviewsAdapter
import com.narcissus.marketplace.ui.products.ProductsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    companion object {
        private const val EXTRA_LEFT_MARGIN = 8
        private const val ROTATION_EXPANDED = 0F
        private const val ROTATION_CONSTRICTED = 180F
    }

    private val viewModel: ProductDetailsViewModel by viewModels()
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
    private val reviewsAdapter = ReviewsAdapter()
    private val similarProductsAdapter = ProductsAdapter {}
    private val aboutProductAdapter = AboutProductAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)
        subscribeViewModel()
        initAboutProductRecyclerView()
        initReviewsRecyclerView()
        initSimilarProductsRecyclerView()
        setOnClickListeners()
        val layoutTransition = binding.root.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun initAboutProductRecyclerView() = with(binding.rvAbout) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = aboutProductAdapter
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

    private fun initSimilarProductsRecyclerView() = with(binding.rvSimilarProducts) {
        layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addItemDecoration(ExtraHorizontalMarginDecoration(EXTRA_LEFT_MARGIN))
        adapter = similarProductsAdapter
    }

    private fun setOnClickListeners() {
        binding.layoutExpandReviewsList.setOnClickListener {
            viewModel.changeReviewsListState()
        }
    }

    private fun subscribeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            subscribeMainProductData()
            Log.d("DEBUG","BEFORE SUBSCRIBEREVIEWS")
            launch {
                subscribeReviews()
            }
            Log.d("DEBUG","AFTER SUBSCRIBEREVIEWS")
            launch {
                subscribeReviewsExpandState()
            }
            launch {
                subscribeReviews()
            }

//            viewModel.productReviewsFlow.onEach{
//                reviewsAdapter.submitItems(it)
//            }.launchIn(this)
        }
    }

    private suspend fun subscribeReviewsExpandState() {
        viewModel.isReviewsListExpandedFlow.collect { isExpanded ->
            with(binding) {
                if (isExpanded) {
                    ivExpandReviewsList.rotation = ROTATION_CONSTRICTED
                    tvExpandReviewsList.text = getString(R.string.hide_all_reviews)
                } else {
                    ivExpandReviewsList.rotation = ROTATION_EXPANDED
                    tvExpandReviewsList.text = getString(R.string.show_all_reviews)
                }
            }
        }
    }

    private suspend fun subscribeMainProductData() {
        viewModel.productDetailsFlow.collect { data ->
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
            }
            aboutProductAdapter.items = mapProductAboutList(data.aboutList)
            similarProductsAdapter.submitItems(data.similarProducts)
        }
    }

    private fun mapProductAboutList(aboutList: List<DetailsAbout>): List<AboutProductItem> {
        val list: MutableList<AboutProductItem> = mutableListOf()
        aboutList.onEach { about ->
            val title = when (about) {
                is DetailsAbout.Type -> getString(R.string.type)
                is DetailsAbout.Color -> getString(R.string.about)
                is DetailsAbout.Material -> getString(R.string.material)
                is DetailsAbout.Description -> getString(R.string.description)
            }
            val data = when (about) {
                is DetailsAbout.Description -> AboutProductItem.MultipleLineItem(title, about.data)
                else -> AboutProductItem.SingleLineItem(title, about.data)
            }
            list.add(data)
        }
        return list
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
