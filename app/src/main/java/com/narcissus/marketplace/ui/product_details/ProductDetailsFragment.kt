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
import coil.transform.RoundedCornersTransformation
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.model.ProductDetails
import com.narcissus.marketplace.ui.home.recycler.ExtraHorizontalMarginDecoration
import com.narcissus.marketplace.ui.product_details.about.AboutProductItem
import com.narcissus.marketplace.ui.product_details.model.DetailsAbout
import com.narcissus.marketplace.ui.product_details.similar.SimilarProductListItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    companion object {
        private const val EXTRA_LEFT_MARGIN = 8
        private const val REVIEWS_AUTHOR_AVATAR_CORNER_RADIUS = 12f
    }

    private val args by navArgs<ProductDetailsFragmentArgs>()

    private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(args.productId) }

    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private val similarProductsAdapter = AsyncListDifferDelegationAdapter(
        SimilarProductListItem.DIFF_CALLBACK,
        SimilarProductListItem.SimilarProductItem.delegate(::navigateToSimilarProduct),
        SimilarProductListItem.SimilarProductLoadingItem.delegate
    )
    private val aboutProductAdapter = AsyncListDifferDelegationAdapter(
        AboutProductItem.DIFF_CALLBACK,
        AboutProductItem.SingleLineItem.delegate,
        AboutProductItem.MultipleLineItem.delegate,
        AboutProductItem.LoadingItem.delegate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProductDetailsBinding.bind(view)
        initAboutRecyclerView()
        initNavigationListeners(args.productId)
        initSimilarProductsRecyclerView()
        initToolbar()
        observeProductDetails()
    }

    private fun initAboutRecyclerView() {
        binding.rvAbout.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvAbout.adapter = aboutProductAdapter
        aboutProductAdapter.items = listOf(AboutProductItem.LoadingItem())
    }

    private fun initNavigationListeners(productId: String) {
        binding.reviewsPreviewLayout.setOnClickListener {
            findNavController().navigate(
                ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductReviewsFragment(
                    productId
                )
            )
        }
    }

    private fun initSimilarProductsRecyclerView() {
        binding.rvSimilarProducts.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSimilarProducts.addItemDecoration(
            ExtraHorizontalMarginDecoration(EXTRA_LEFT_MARGIN)
        )
        binding.rvSimilarProducts.adapter = similarProductsAdapter
        similarProductsAdapter.items = listOf(SimilarProductListItem.SimilarProductLoadingItem())
    }

    private fun initToolbar() {
        val navController = findNavController()
        val configuration = AppBarConfiguration(navController.graph)
        binding.tbTop.setupWithNavController(navController, configuration)
    }

    private fun observeProductDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.productDetailsFlow.collect { product ->
                renderProductImage(product)
                renderProductInfo(product)
                initPurchaseButton(product)
                renderAboutProduct(product)
                renderProductReviews(product)
                renderSimilarProducts(product)
            }
        }
    }

    private fun renderProductImage(product: ProductDetails) {
        binding.ivProduct.load(product.icon) {
            listener(
                onSuccess = { _, _ ->
                    hideShimmerImage()
                }
            )
        }
    }

    private fun renderProductInfo(product: ProductDetails) {
        binding.tvProductName.text = product.name
        binding.ratingBarProduct.progress = product.rating
        binding.tvPrice.text = getString(R.string.price_placeholder, product.price)
        binding.tvSales.text = getString(R.string.sales_placeholder, product.sales)
        binding.tvStock.text = getString(R.string.in_stock_placeholder, product.stock)
    }

    private fun initPurchaseButton(product: ProductDetails) {
        binding.btnPurchase.setOnClickListener {
            viewModel.purchase(product)
        }
    }

    private fun renderAboutProduct(product: ProductDetails) {
        aboutProductAdapter.items = mapProductAboutList(product.getAboutDetails())
    }

    private fun renderProductReviews(product: ProductDetails) {
        if (product.reviews.isEmpty()) return
        binding.tvReviewsPreviewAuthor.text = product.reviews[0].author
        binding.tvReviewsPreviewDescription.text = product.reviews[0].details
        binding.reviewsPreviewRatingBar.progress = product.reviews[0].rating
        binding.ivReviewsPreviewAvatar.load(product.reviews[0].reviewAuthorIcon) {
            transformations(
                RoundedCornersTransformation(
                    REVIEWS_AUTHOR_AVATAR_CORNER_RADIUS
                )
            )
        }
    }

    private fun renderSimilarProducts(product: ProductDetails) {
        similarProductsAdapter.items = product.similarProducts.map {
            SimilarProductListItem.SimilarProductItem(it)
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
                is DetailsAbout.Color -> getString(R.string.color)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToSimilarProduct(productId: String) {
        findNavController().navigate(
            ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductDetailsFragment(
                productId
            )
        )
    }

    private fun ProductDetails.getAboutDetails(): List<DetailsAbout> =
        listOf(
            DetailsAbout.Type(type),
            DetailsAbout.Color(color),
            DetailsAbout.Material(material),
            DetailsAbout.Description(description)
        )
}
