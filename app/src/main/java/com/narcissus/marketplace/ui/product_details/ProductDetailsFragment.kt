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
import com.narcissus.marketplace.core.navigation.NavDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.ui.cart.di.CartDestination
import com.narcissus.marketplace.ui.product_details.di.ProductDetailsDestination
import com.narcissus.marketplace.ui.product_details.model.ToolbarData
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.qualifier
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
        binding.toolBarDetails.setupWithNavController(navController)
        binding.appBarDetailsLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val hasScrollFinished = abs(verticalOffset) - appBarLayout.totalScrollRange == 0
                binding.collapsingToolbarDetailsDivider.visibility =
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
            .onEach(detailsAdapter!!::setItems)
            .launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun renderToolbar(toolbarData: ToolbarData) {
        binding.collapsingToolbarDetailsLayout.title = toolbarData.productName
        binding.ivProductMain.load(toolbarData.productIcon) {
            listener(
                onSuccess = { _, _ -> hideShimmerImage() },
            )
        }
    }

    private fun hideShimmerImage() {
        binding.shimmerProductMainImagePlaceHolder.visibility = View.GONE
    }

    private fun navigateToSimilarProduct(id: String, cardView: MaterialCardView) {
        val catalogDestination: NavDestination by inject(
            qualifier<ProductDetailsDestination>()
        ) {
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
        val cartDestination: NavDestination by inject(qualifier<CartDestination>())
        navigator.navigate(cartDestination)
    }
}
