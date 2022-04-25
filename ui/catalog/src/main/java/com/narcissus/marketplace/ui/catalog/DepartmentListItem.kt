package com.narcissus.marketplace.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import coil.load
import com.narcissus.marketplace.ui.catalog.databinding.ListItemDepartmentBinding
import com.narcissus.marketplace.ui.catalog.databinding.ListItemLoadingDepartmentBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.domain.model.Department

typealias DepartmentBinding = ListItemDepartmentBinding
typealias LoadingDepartmentBinding = ListItemLoadingDepartmentBinding

sealed class DepartmentListItem {
    data class DepartmentItem(val department: Department) : DepartmentListItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = DepartmentBinding.inflate(inflater, parent, false)

            fun delegate() =
                adapterDelegateViewBinding<DepartmentItem, DepartmentListItem, DepartmentBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvName.text = item.department.name
                        binding.ivImage.load(item.department.imageUrl) {
                            transformations(GradientTransformation())
                        }
                    }
                }
        }
    }

    class LoadingDepartmentItem : DepartmentListItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                inflater: LayoutInflater,
                parent: ViewGroup,
            ) = LoadingDepartmentBinding.inflate(inflater, parent, false)

            fun delegate() =
                adapterDelegateViewBinding<LoadingDepartmentItem, DepartmentListItem, LoadingDepartmentBinding>(
                    ::inflateBinding
                ) {
                }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DepartmentListItem>() {
            override fun areItemsTheSame(
                oldItem: DepartmentListItem,
                newItem: DepartmentListItem,
            ): Boolean {
                return when (oldItem) {
                    is DepartmentItem -> newItem is DepartmentItem && oldItem.department.departmentId == newItem.department.departmentId
                    is LoadingDepartmentItem -> newItem is LoadingDepartmentItem && oldItem === newItem
                }
            }

            override fun areContentsTheSame(
                oldItem: DepartmentListItem,
                newItem: DepartmentListItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
