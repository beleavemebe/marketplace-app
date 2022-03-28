package com.narcissus.marketplace.ui.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.narcissus.marketplace.domain.usecase.GetDepartments
import com.narcissus.marketplace.ui.catalog.DepartmentListItem.DepartmentItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn

class CatalogViewModel(
    private val getDepartments: GetDepartments,
) : ViewModel() {
    companion object {
        private const val DEPARTMENTS_AMOUNT = 14
    }

    val departments: Flow<List<DepartmentListItem>> = flow {
        val dummyDepartments = Array(DEPARTMENTS_AMOUNT) {
            DepartmentListItem.LoadingDepartmentItem()
        }
        emit(dummyDepartments.toList())

        val departments = getDepartments().map(::DepartmentItem)
        emit(departments)
    }.shareIn(
        viewModelScope,
        SharingStarted.Lazily,
        1
    )
}
