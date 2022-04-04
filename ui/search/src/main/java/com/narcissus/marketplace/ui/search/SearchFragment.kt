package com.narcissus.marketplace.ui.search

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val args by navArgs<SearchFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.tvContent).text =
            args.query + " " + args.department + " " + args.sortBy
    }
}
