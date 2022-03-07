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
import coil.transform.RoundedCornersTransformation
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
        private const val REVIEWS_AUTHOR_AVATAR_CORNER_RADIUS = 12f
    }

    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(args.productId) }
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!
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
        observeProductDetails()
        initAboutRecyclerView()
        initListeners(args.productId)
        initSimilarProductsRecyclerView()
        initToolBar()
        initLayoutAnimation()
    }

    private fun initListeners(productId: String) {
        binding.reviewsPreviewLayout.setOnClickListener {
            findNavController().navigate(
                ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductReviewsFragment(productId)
            )
            }
        }


    private fun initAboutRecyclerView() = with(binding.rvAbout) {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = aboutProductAdapter
        itemAnimator = null
        aboutProductAdapter.items = listOf(AboutProductItem.LoadingItem())
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

    private fun navigateToSimilarProduct(productId: String) {
        findNavController().navigate(
            ProductDetailsFragmentDirections.actionProductDetailsFragmentToProductDetailsFragment(
                productId
            )
        )
    }

    private fun observeProductDetails() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
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
                    tvReviewsPreviewAuthor.text = data.reviews[0].author
                    tvReviewsPreviewDescription.text = data.reviews[0].details
                    reviewsPreviewRatingBar.progress = data.reviews[0].rating
                    ivReviewsPreviewAvatar.load(data.reviews[0].reviewAuthorIcon){
                        transformations(RoundedCornersTransformation(REVIEWS_AUTHOR_AVATAR_CORNER_RADIUS ))
                    }
                }
                aboutProductAdapter.items = mapProductAboutList(data.aboutList)
                similarProductsAdapter.submitItems(data.similarProducts)

            }
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
