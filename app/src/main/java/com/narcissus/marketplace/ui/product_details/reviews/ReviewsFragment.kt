package com.narcissus.marketplace.ui.product_details.reviews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentProductDetailsBinding
import com.narcissus.marketplace.databinding.FragmentReviewsBinding
import com.narcissus.marketplace.ui.product_details.ProductDetailsFragmentArgs
import com.narcissus.marketplace.ui.product_details.ProductDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf


class ReviewsFragment : Fragment(R.layout.fragment_reviews),KoinComponent {
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
        initToolBar()
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
    private fun observeReviews(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.productDetailsFlow.collect{ details->
                reviewsAdapter.items=details.reviews.map { ReviewsItem.ReviewItem(it) }
            }
        }
    }

    private fun initToolBar() {
        val navController = findNavController()
        binding.tbTop
            .setupWithNavController(navController, AppBarConfiguration(navController.graph))
    }

}
