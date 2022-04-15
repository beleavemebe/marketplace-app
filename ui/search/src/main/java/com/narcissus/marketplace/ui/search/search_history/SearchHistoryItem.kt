package com.narcissus.marketplace.ui.search.search_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.narcissus.marketplace.core.databinding.ListItemDividerBinding
import com.narcissus.marketplace.ui.search.databinding.ListItemSearchHistoryBinding
sealed class SearchHistoryItem {
    data class HistoryItem(val itemName:String): SearchHistoryItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = ListItemSearchHistoryBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<HistoryItem, SearchHistoryItem, ListItemSearchHistoryBinding>(
                    ::inflateBinding,
                ) {
                    bind {
                        binding.tvSearchHistoryItemName.text = item.itemName
                    }
                }
        }
    }

    class Divider : SearchHistoryItem() {
        companion object {
            @JvmStatic
            private fun inflateBinding(
                layoutInflater: LayoutInflater,
                root: ViewGroup,
            ) = ListItemDividerBinding.inflate(layoutInflater, root, false)

            fun delegate() =
                adapterDelegateViewBinding<Divider, SearchHistoryItem,ListItemDividerBinding>(
                    ::inflateBinding,
                ) {
                    bind {}
                }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchHistoryItem>() {
            override fun areItemsTheSame(
                oldItem: SearchHistoryItem,
                newItem: SearchHistoryItem,
            ): Boolean = when(oldItem){
                is HistoryItem-> newItem is HistoryItem &&newItem.itemName==oldItem.itemName
                is Divider->newItem is Divider
            }


            override fun areContentsTheSame(
                oldItem: SearchHistoryItem,
                newItem: SearchHistoryItem,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}
