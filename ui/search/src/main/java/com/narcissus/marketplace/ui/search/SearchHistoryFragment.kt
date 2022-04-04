package com.narcissus.marketplace.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.narcissus.marketplace.core.navigation.destination.SearchDestination
import com.narcissus.marketplace.core.navigation.navigator
import com.narcissus.marketplace.domain.model.search.SortBy
import com.narcissus.marketplace.ui.search.databinding.FragmentSearchHistoryBinding

class SearchHistoryFragment : Fragment(R.layout.fragment_search_history) {
    private var _binding: FragmentSearchHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchHistoryBinding.bind(view)
        binding.searchView.root.setOnClickListener {
            navigator.navigate(
                SearchDestination("Query", "Department", SortBy.RATING)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
