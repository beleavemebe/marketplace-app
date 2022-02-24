package com.narcissus.marketplace.ui.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCatalogBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CatalogFragment : Fragment(R.layout.fragment_catalog) {
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val catalogViewModel: CatalogViewModel by viewModels()
    private val catalogAdapter = CatalogAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCatalogBinding.bind(view)

        setDepartmentList()
        setCatalogRecyclerView()

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
