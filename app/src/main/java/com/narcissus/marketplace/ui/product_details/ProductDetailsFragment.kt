package com.narcissus.marketplace.ui.product_details

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.model.DetailsAbout
import com.narcissus.marketplace.ui.home.recycler.ExtraHorizontalMarginDecoration
import com.narcissus.marketplace.ui.product_details.about.AboutProductItem
import com.narcissus.marketplace.ui.product_details.reviews.DividerItemDecorator
import com.narcissus.marketplace.ui.product_details.reviews.ReviewsItem
import com.narcissus.marketplace.ui.products.ProductsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    companion object {
        private const val EXTRA_LEFT_MARGIN = 8
        private const val ROTATION_EXPANDED = 180F
        private const val ROTATION_COLLAPSED = 0F
    }

    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(args.productId) }
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private val reviewsAdapter = ListDelegationAdapter(
        ReviewsItem.ReviewItem.delegate,
        ReviewsItem.LoadingItem.delegate
    )
    private val similarProductsAdapter = ProductsAdapter(::navigateToSimilarProduct)
    private val aboutProductAdapter = AsyncListDifferDelegationAdapter(
        AboutProductItem.DIFF_CALLBACK,
        AboutProductItem.SingleLineItem.delegate,
        AboutProductItem.MultipleLineItem.delegate,
        AboutProductItem.LoadingItem.delegate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)
        subscribeToViewModel()
        initAboutRecyclerView()
        initReviewsRecyclerView()
        initSimilarProductsRecyclerView()
        initToolBar()
        initListeners()
        initLayoutAnimation()
    }

    private fun initAboutRecyclerView() = with(binding.rvAbout) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = aboutProductAdapter
        itemAnimator = null
        aboutProductAdapter.items = listOf(AboutProductItem.LoadingItem())
    }

    private fun initReviewsRecyclerView() = with(binding.rvReviews) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = reviewsAdapter
        addItemDecoration(
            DividerItemDecorator(
                ContextCompat.getDrawable(requireContext(), R.drawable.recycler_view_divider)!!
            )
        )
        itemAnimator = null
        reviewsAdapter.submitItems(listOf(ReviewsItem.LoadingItem()))
    }

    private fun initSimilarProductsRecyclerView() = with(binding.rvSimilarProducts) {
        layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        addItemDecoration(ExtraHorizontalMarginDecoration(EXTRA_LEFT_MARGIN))
        adapter = similarProductsAdapter
        itemAnimator = null
    }

    private fun initToolBar() {
        val navController = findNavController()
        binding.tbTop
            .setupWithNavController(navController, AppBarConfiguration(navController.graph))
    }

    private fun initLayoutAnimation() {
        val layoutTransition = binding.root.layoutTransition
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun initListeners() {
        binding.layoutExpandReviewsList.setOnClickListener {
            viewModel.changeReviewsListState()
        }
    }

    private fun navigateToSimilarProduct(productId: String) {
        findNavController().navigate(
            ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductDetailsFragment(
                productId
            )
        )
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                observeProductDetails()
            }
            launch {
                observeReviews()
            }
            launch {
                observeIsReviewListExpanded()
            }
        }
    }

    private suspend fun observeProductDetails() {
        viewModel.productDetailsFlow.collect { data ->
            with(binding) {
                ivProduct.load(data.icon) {
                    listener(
                        onSuccess = { _, _ ->
                            hideShimmerImage()
                        }
                    )
                }
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

    private fun hideShimmerImage() {
        binding.productImageShimmer.visibility = View.GONE
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

    private suspend fun observeReviews() {
        viewModel.reviewsFlow.collect { reviewList ->
            reviewsAdapter.submitItems(reviewList.map { ReviewsItem.ReviewItem(it) })
        }
    }

    private suspend fun observeIsReviewListExpanded() {
        viewModel.isReviewListExpandedFlow.collect { isExpanded ->
            with(binding) {
                if (isExpanded) {
                    ivExpandReviewsList.rotation = ROTATION_EXPANDED
                    tvExpandReviewsList.text = getString(R.string.hide_all_reviews)
                } else {
                    ivExpandReviewsList.rotation = ROTATION_COLLAPSED
                    tvExpandReviewsList.text = getString(R.string.show_all_reviews)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged") // animation doesn't work well with diffutils
    fun <T> ListDelegationAdapter<List<T>>.submitItems(items: List<T>) {
        this.items = items
        this.notifyDataSetChanged()
    }
}
