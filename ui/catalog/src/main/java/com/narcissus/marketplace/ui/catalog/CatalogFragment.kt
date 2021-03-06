package com.narcissus.marketplace.ui.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialFadeThrough
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.ui.catalog.databinding.FragmentCatalogBinding
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by viewModel()

    private val catalogAdapter by lazy {
        AsyncListDifferDelegationAdapter(
            DepartmentListItem.DIFF_CALLBACK,
            DepartmentListItem.DepartmentItem.delegate(),
            DepartmentListItem.LoadingDepartmentItem.delegate(),
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
//        val sv = binding.searchLayout.findViewById<SearchView>(CORE.id.searchView)
//        sv.setOnQueryTextFocusChangeListener { _, _ ->
//            sv.setOnQueryTextFocusChangeListener(null)
//            navigateToSearch()
//        }
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
