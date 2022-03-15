package com.narcissus.marketplace.ui.product_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.appbar.AppBarLayout
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.ui.product_details.model.ToolBarData
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.abs

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(args.productId) }
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private val detailsAdapter = ProductDetailsAdapter(
        purchaseClicked = ::purchase,
        goToCartClicked = ::goToCart,
        allReviewsClicked = ::navigateToReviews,
        reviewTextClicked = ::changeReviewExpandedState,
        similarProductClicked = ::navigateToSimilarProduct,
        addSimilarProductToCartClicked = ::addSimilarProductToCart,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)
        initToolbar()
        initDetailsRecyclerView()
        setProductDetailsLoadingState()
        observeProductDetails()
        viewModel.collapseReviewState()
    }

    private fun setProductDetailsLoadingState() {
        detailsAdapter.items = listOf(
            ProductDetailsItem.ProductMainInfoPlaceHolder(),
            ProductDetailsItem.ProductDetailsPlaceHolder(),
            ProductDetailsItem.ProductDetailsPlaceHolder(),
            ProductDetailsItem.ProductDetailsPlaceHolder(),
        )
    }

    private fun initDetailsRecyclerView() {
        binding.rvDetails.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDetails.adapter = detailsAdapter
        binding.rvDetails.smoothScrollToPosition(0)
        binding.rvDetails.itemAnimator = null
    }

    private fun changeReviewExpandedState() {
        viewModel.changeReviewExpandedState()
    }

    private fun initToolbar() {
        val navController = findNavController()
        val configuration = AppBarConfiguration(navController.graph)
        binding.toolBarDetails.setupWithNavController(navController, configuration)
        binding.appBarDetailsLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0)
                    binding.collapsingToolbarDetailsDivider.visibility = View.VISIBLE
                else binding.collapsingToolbarDetailsDivider.visibility = View.INVISIBLE
            },
        )
    }

    private fun observeProductDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            launch {
                viewModel.productDetailsToolBarFlow.collect { toolBarData ->
                    renderToolBar(toolBarData)
                }
            }
            launch {
                viewModel.productDetailsFlow.collect { details ->
                    detailsAdapter.items = details
                }
            }
        }
    }

    private fun renderToolBar(toolBarData: ToolBarData) {
        binding.ivProductMain.load(toolBarData.productIcon) {
            listener(
                onSuccess = { _, _ ->
                    hideShimmerImage()
                },
            )
        }
        binding.collapsingToolbarDetailsLayout.title = toolBarData.productName
    }

    private fun hideShimmerImage() {
        binding.shimmerProductMainImagePlaceHolder.visibility = View.GONE
    }

    private fun navigateToSimilarProduct(productId: String) {
        findNavController().navigate(
            ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductDetailsFragment(
                productId,
            ),
        )
    }

    private fun navigateToReviews() {
        findNavController().navigate(
            ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductReviewsFragment(
                viewModel.reviewsFlow.value.toTypedArray(),
            ),
        )
    }

    private fun addSimilarProductToCart(productId: String) {
    }

    private fun purchase() {
        viewModel.purchase()
    }

    private fun goToCart() {
    }
}
