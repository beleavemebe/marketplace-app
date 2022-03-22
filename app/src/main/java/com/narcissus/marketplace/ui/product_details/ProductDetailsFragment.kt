package com.narcissus.marketplace.ui.product_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialContainerTransform
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.core.navigation.destination.CartDestination
import com.narcissus.marketplace.core.navigation.destination.ProductDetailsDestination
import com.narcissus.marketplace.ui.product_details.model.ToolbarData
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.abs

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ProductDetailsFragmentArgs>()

    private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(args.productId) }

    private var detailsAdapter: ProductDetailsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = buildContainerTransform(true)
        sharedElementReturnTransition = buildContainerTransform(false)
    }

    private fun buildContainerTransform(
        entering: Boolean,
    ) = MaterialContainerTransform(requireContext(), entering).apply {
        drawingViewId = R.id.nav_host_fragment
        interpolator = FastOutSlowInInterpolator()
        containerColor = MaterialColors.getColor(
            requireActivity().findViewById(android.R.id.content),
            com.google.android.material.R.attr.colorSurface,
        )
        fadeMode = MaterialContainerTransform.FADE_MODE_OUT
        duration = 300
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)
        binding.root.transitionName = args.productId
        detailsAdapter = createAdapter()
        initToolbar()
        initDetailsRecyclerView()
        setProductDetailsLoadingState()
        observeProductDetails()
    }

    private fun createAdapter(): ProductDetailsAdapter =
        ProductDetailsAdapter(
            purchaseClicked = viewModel::purchase,
            goToCartClicked = ::goToCart,
            allReviewsClicked = ::navigateToReviews,
            lifecycle = viewLifecycleOwner.lifecycle,
            scope = viewLifecycleOwner.lifecycleScope,
            onSimilarProductClicked = ::navigateToSimilarProduct,
            onAddSimilarProductToCartClicked = ::addSimilarProductToCart,
        )

    private fun setProductDetailsLoadingState() {
        detailsAdapter?.items = listOf(
            ProductDetailsItem.LoadingMainProductInfo(),
            ProductDetailsItem.LoadingProductDetails(),
            ProductDetailsItem.LoadingProductDetails(),
            ProductDetailsItem.LoadingProductDetails(),
        )
    }

    private fun initDetailsRecyclerView() {
        binding.rvDetails.adapter = detailsAdapter
        binding.rvDetails.smoothScrollToPosition(0)
        binding.rvDetails.itemAnimator = null
    }

    private fun initToolbar() {
        val navController = findNavController()
        binding.toolbar.setupWithNavController(navController)
        binding.appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val diff = abs(verticalOffset) - appBarLayout.totalScrollRange
                val hasScrollFinished = diff == 0
                binding.collapsingToolbarDivider.visibility =
                    if (hasScrollFinished) {
                        View.VISIBLE
                    } else {
                        View.INVISIBLE
                    }
            },
        )
    }

    private fun observeProductDetails() {
        viewModel.productDetailsToolbarFlow
            .onEach(::renderToolbar)
            .launchWhenStarted(viewLifecycleOwner.lifecycleScope)

        viewModel.contentFlow
            .onEach(::renderContent)
            .launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderToolbar(toolbarData: ToolbarData) {
        binding.collapsingToolbarLayout.title = toolbarData.productName
        binding.ivProduct.load(toolbarData.productIcon) {
            listener(
                onSuccess = { _, _ -> hideShimmerImage() },
            )
        }
    }

    private fun renderContent(items: List<ProductDetailsItem>) {
        detailsAdapter?.items = items
    }

    private fun hideShimmerImage() {
        binding.shimmerProductImage.visibility = View.GONE
    }

    private fun navigateToSimilarProduct(id: String, cardView: MaterialCardView) {
        val catalogDestination by inject<ProductDetailsDestination> {
            parametersOf(id)
        }

        val extras = FragmentNavigatorExtras(cardView to id)
        navigator.navigate(catalogDestination, extras)
    }

    private fun navigateToReviews() {
        findNavController().navigate(
            ProductDetailsFragmentDirections.navToProductReviews(
                viewModel.reviewsFlow.value.toTypedArray(),
            ),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        detailsAdapter = null
    }

    private fun addSimilarProductToCart(productId: String) {
    }


    private fun goToCart() {
        val cartDestination by inject<CartDestination>()
        navigator.navigate(cartDestination)
    }
}
