package com.narcissus.marketplace.ui.catalog

import androidx.lifecycle.ViewModel
import com.narcissus.marketplace.R

class CatalogViewModel:ViewModel() {



    fun getDepartmentList(): List<DepartmentModel>{
        return listOf(
           DepartmentModel(
                name = "Gadgets",
                image = "gadgets.jpg"
            ),
            DepartmentModel(
                name = "Appliance",
                image ="appliance.jpg"
            ),
            DepartmentModel(
                name = "Home & Living",
                image = "homeandliving.jpg"
            ),
            DepartmentModel(
                name = "School Supplies",
                image = "schoolsupplies.jpg"
            ),
            DepartmentModel(
                name = "Health & Beauty",
                image = "healthandbeauty.jpg"
            ),
            DepartmentModel(
                name = "For Babies",
                image = "babies.jpg"
            ),
            DepartmentModel(
                name = "Groceries",
                image = "groceries.jpg"
            ),
            DepartmentModel(
                name = "For Pets",
                image = "pets.jpg"
            ),
            DepartmentModel(
                name = "Fashion Women",
                image = "fashionwomen.jpg"
            ),
            DepartmentModel(
                name = "Fashion Men",
                image = "fashionmen.jpg"
            ),
            DepartmentModel(
                name = "Accessories",
                image = "accessories.jpg"
            ),
            DepartmentModel(
                name = "Sports & Lifestyle",
                image = "sport.png"
            ),
            DepartmentModel(
                name = "Toys & Collectibles",
                image = "toys.jpg"
            ),
            DepartmentModel(
                name = "Automotive",
                image = "automotive.jpg"
            )


        )
    }
}