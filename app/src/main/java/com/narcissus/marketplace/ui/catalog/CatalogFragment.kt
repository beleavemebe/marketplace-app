package com.narcissus.marketplace.ui.catalog

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCatalogBinding
import com.narcissus.marketplace.databinding.FragmentFilterDialogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val catalogViewModel: CatalogViewModel by viewModels()
    private val catalogAdapter = CatalogAdapter()
    private val searchHistoryViewModel: SearchHistoryViewModel by viewModels()
    private val searchHistoryAdapterAdapter = SearchHistoryAdapter()

//    private val supportFragmentManager = activity?.supportFragmentManager
//    private val navController =
//        (supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
//            .navController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatalogBinding.bind(view)

        setDepartmentList()
        setCatalogRecyclerView()
        setSearchListeners()
        setSearchHistoryList()
        setSearchHistoryRecyclerView()


//        binding.searchLayout.cvFilter.setOnClickListener {
//            val dialogFragment = FilterDialogFragment()
//            supportFragmentManager?.beginTransaction()
//                //                .add(R.id.fragment_filter_dialog,dialogFragment)
//                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                ?.addToBackStack("yFragment")
//                ?.commit()
//            }
//        }
    }

    private fun setSearchListeners() {
//        binding.searchLayout.etSearch.setOnFocusChangeListener { view, hasFocus ->
//            if (view.hasFocus()) {
//                binding.searchLayout.etSearch.background = ContextCompat.getDrawable(
//                    requireContext(),
//                    com.narcissus.marketplace.R.drawable.background_search
//                )
//                binding.searchLayout.rvSearchHistory.visibility = View.VISIBLE
//            }
//            else {binding.searchLayout.etlSearch.background = null
//            binding.searchLayout.rvSearchHistory.visibility=View.GONE
//            }
//        }
//
//        binding.searchLayout.cvFilter.setOnClickListener {
//
//        }
    }

    private fun setDepartmentList() {
        lifecycleScope.launch {
            flow {
                emit(catalogViewModel.getDepartmentList())
            }.flowOn(Dispatchers.IO)
                .collect {
                    catalogAdapter.departmentList = it
                }
        }
    }

    private fun setCatalogRecyclerView() {
        binding.rvDepartment.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
            adapter = catalogAdapter
        }
    }

    private fun setSearchHistoryList() {
        lifecycleScope.launch {
            flow {
                emit(searchHistoryViewModel.getSearchHistoryList())
            }.flowOn(Dispatchers.IO)
                .collect {
                    searchHistoryAdapterAdapter.searchHistoryList = it
                }
        }
    }

    private fun setSearchHistoryRecyclerView() {
        binding.searchLayout.rvSearchHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(
                requireContext()
            )
            adapter = searchHistoryAdapterAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
