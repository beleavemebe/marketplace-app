package com.narcissus.marketplace.ui.product_details.reviews

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
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentReviewsBinding
import com.narcissus.marketplace.ui.product_details.ProductDetailsFragmentArgs
import com.narcissus.marketplace.ui.product_details.ProductDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class ReviewsFragment : Fragment(R.layout.fragment_reviews), KoinComponent {
    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ProductDetailsFragmentArgs>()

    private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(args.productId) }

    private val reviewsAdapter = AsyncListDifferDelegationAdapter(
        ReviewsItem.DIFF_CALLBACK,
        ReviewsItem.ReviewItem.delegate
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReviewsBinding.bind(view)
        initReviewsRecyclerView()
        observeReviews()
        initToolbar()
    }

    private fun initReviewsRecyclerView() {
        binding.rvReviews.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvReviews.adapter = reviewsAdapter
        binding.rvReviews.addItemDecoration(
            DividerItemDecoration(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.recycler_view_divider
                )!!
            )
        )
    }

    private fun observeReviews() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.productDetailsFlow.collect { details ->
                reviewsAdapter.items = details.reviews.map { ReviewsItem.ReviewItem(it) }
            }
        }
    }

    private fun initToolbar() {
        val navController = findNavController()
        val configuration = AppBarConfiguration(navController.graph)
        binding.tbTop.setupWithNavController(navController, configuration)
    }
}
