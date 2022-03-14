package com.narcissus.marketplace.ui.product_details.reviews

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentReviewsBinding

class ReviewsFragment : Fragment(R.layout.fragment_reviews) {
    private var _binding: FragmentReviewsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ReviewsFragmentArgs>()
    private val reviews by lazy { args.productReviews }
    private val reviewsAdapter = AsyncListDifferDelegationAdapter(
        ReviewsItem.DIFF_CALLBACK,
        ReviewsItem.ReviewItem.delegate,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReviewsBinding.bind(view)
        initReviewsRecyclerView()
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
                    R.drawable.recycler_view_divider,
                )!!,
            ),
        )
        reviewsAdapter.items = reviews.toList().map { ReviewsItem.ReviewItem(it) }
    }

    private fun initToolbar() {
        val navController = findNavController()
        val configuration = AppBarConfiguration(navController.graph)
        binding.tbTop.setupWithNavController(navController, configuration)
    }
}
