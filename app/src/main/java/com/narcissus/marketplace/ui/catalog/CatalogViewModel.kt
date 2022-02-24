package com.narcissus.marketplace.ui.catalog

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.R

class CatalogViewModel:ViewModel() {

    fun getDepartmentList(): List<DepartmentModel>{
        return listOf(
           DepartmentModel(
                name = "Electronics",
                image = com.narcissus.marketplace.R.drawable.ic_hint
            ),
            DepartmentModel(
                name = "Clothes",
                image =com.narcissus.marketplace.R.drawable.ic_hint
            ),
            DepartmentModel(
                name = "Books",
                image = com.narcissus.marketplace.R.drawable.ic_hint
            ),
            DepartmentModel(
                name = "Furniture",
                image = com.narcissus.marketplace.R.drawable.ic_hint
            ),
            DepartmentModel(
                name = "Food",
                image = com.narcissus.marketplace.R.drawable.ic_hint
            ),
        )
    }
}