package com.narcissus.marketplace.ui.catalog

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CatalogViewModel : ViewModel() {
    val departments: Flow<List<DepartmentListItem>> = flow {
        emit(
            listOf(
                DepartmentListItem(
                    name = "Gadgets",
                    image = "gadgets.jpg"
                ),
                DepartmentListItem(
                    name = "Appliance",
                    image = "appliance.jpg"
                ),
                DepartmentListItem(
                    name = "Home & Living",
                    image = "homeandliving.jpg"
                ),
                DepartmentListItem(
                    name = "School Supplies",
                    image = "schoolsupplies.jpg"
                ),
                DepartmentListItem(
                    name = "Health & Beauty",
                    image = "healthandbeauty.jpg"
                ),
                DepartmentListItem(
                    name = "For Babies",
                    image = "babies.jpg"
                ),
                DepartmentListItem(
                    name = "Groceries",
                    image = "groceries.jpg"
                ),
                DepartmentListItem(
                    name = "For Pets",
                    image = "pets.jpg"
                ),
                DepartmentListItem(
                    name = "Fashion Women",
                    image = "fashionwomen.jpg"
                ),
                DepartmentListItem(
                    name = "Fashion Men",
                    image = "fashionmen.jpg"
                ),
                DepartmentListItem(
                    name = "Accessories",
                    image = "accessories.jpg"
                ),
                DepartmentListItem(
                    name = "Sports & Lifestyle",
                    image = "sport.png"
                ),
                DepartmentListItem(
                    name = "Toys & Collectibles",
                    image = "toys.jpg"
                ),
                DepartmentListItem(
                    name = "Automotive",
                    image = "automotive.jpg"
                )
            )
        )
    }
}
