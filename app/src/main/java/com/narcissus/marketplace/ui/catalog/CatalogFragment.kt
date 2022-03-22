package com.narcissus.marketplace.ui.catalog

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialFadeThrough
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentCatalogBinding
import kotlinx.coroutines.flow.onEach

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by viewModels()

    private val catalogAdapter by lazy {
        ListDelegationAdapter(
            DepartmentListItem.delegate(viewLifecycleOwner.lifecycleScope),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatalogBinding.bind(view)
        renderTransition()
        initDepartmentsRecyclerView()
        initSearchViewListener()
        subscribeToViewModel()
    }

    private fun renderTransition() {
        postponeEnterTransition()
        binding.rvDepartment.post { startPostponedEnterTransition() }
    }

    private fun initDepartmentsRecyclerView() {
        binding.rvDepartment.adapter = catalogAdapter
    }

    private fun initSearchViewListener() {
        val sv = binding.searchLayout.findViewById<SearchView>(R.id.searchView)
        sv.setOnQueryTextFocusChangeListener { _, _ ->
            sv.setOnQueryTextFocusChangeListener(null)
            navigateToSearch()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.departments
            .onEach(catalogAdapter::setItems)
            .launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToSearch() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
