package com.narcissus.marketplace.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.narcissus.marketplace.ui.search.databinding.FragmentSearchHistoryBinding

class SearchFragment : Fragment(R.layout.fragment_search_history) {
    private var _binding: FragmentSearchHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchHistoryBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
