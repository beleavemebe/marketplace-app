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
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
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

    private val aboutAdapter = ListDelegationAdapter(
        AboutItem.SingleLineItem.delegate,
        AboutItem.MultipleLineItem.delegate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)
        subscribeViewModel()
        initAboutRecyclerView()
        initReviewsRecyclerView()
        initSimilarProductsRecyclerView()
        setOnClickListeners()
        val layoutTransition = binding.root.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun initAboutRecyclerView() = with(binding.rvAbout){
        layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter=aboutAdapter
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
            aboutAdapter.items=mapProductAboutList(data.aboutList)
            similarAdapter.submitItems(data.similarProducts)
        }
    }

    private fun mapProductAboutList(aboutList:List<DetailsAbout>): List<AboutItem> {
        val list:MutableList<AboutItem> = mutableListOf()
        aboutList.onEach { about->
            val title = when(about){
                is DetailsAbout.Type ->getString(R.string.type)
                is DetailsAbout.Color -> getString(R.string.about)
                is DetailsAbout.Material -> getString(R.string.material)
                is DetailsAbout.Description -> getString(R.string.description)
            }
            val data = when(about){
                is DetailsAbout.Description->AboutItem.MultipleLineItem(title,about.data)
                else ->AboutItem.SingleLineItem(title,about.data)
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
