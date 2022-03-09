package com.narcissus.marketplace.ui.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by viewModels()

    private val catalogAdapter by lazy {
        ListDelegationAdapter(
            DepartmentListItem.delegate(viewLifecycleOwner.lifecycleScope),
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatalogBinding.bind(view)
        initDepartmentsRecyclerView()
        subscribeToViewModel()
    }

    private fun initDepartmentsRecyclerView() {
        binding.rvDepartment.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvDepartment.adapter = catalogAdapter
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.departments.collect(catalogAdapter::setItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
