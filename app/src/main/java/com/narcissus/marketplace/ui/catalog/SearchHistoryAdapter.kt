package com.narcissus.marketplace.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.narcissus.marketplace.databinding.ItemSearchHistoryRowBinding

class SearchHistoryAdapter: RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    var searchHistoryList: List<SearchHistoryModel> = emptyList()

    inner class SearchHistoryViewHolder(
        val binding: ItemSearchHistoryRowBinding,
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchHistoryRowBinding.inflate(inflater, parent, false)
        return SearchHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val searchHistory = (searchHistoryList[position])
        holder.binding.tvSuggestion.text = searchHistory.suggestion
    }

    override fun getItemCount(): Int {
        return searchHistoryList.size
    }
}

data class SearchHistoryModel(
    val suggestion:String
)