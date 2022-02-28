package com.narcissus.marketplace.ui.catalog


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.databinding.ItemDepartmentCardBinding

class CatalogAdapter: RecyclerView.Adapter<CatalogAdapter.DepartmentViewHolder>() {

    var departmentList: List<DepartmentModel> = emptyList()

    inner class DepartmentViewHolder(
        val binding: ItemDepartmentCardBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDepartmentCardBinding.inflate(inflater, parent, false)
        return DepartmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val department = (departmentList[position])
        holder.binding.name.text = department.name
        holder.binding.image.setImageResource(department.image)
    }

    override fun getItemCount(): Int {
        return departmentList.size
    }
}

data class DepartmentModel(
    val name:String,
    val image:Int
)