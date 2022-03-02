package com.narcissus.marketplace.ui.catalog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCatalogBinding
import com.narcissus.marketplace.databinding.FragmentFilterDialogBinding


class FilterDialogFragment : DialogFragment() {
    private var _binding: FragmentFilterDialogBinding? = null
    private val binding get() = _binding!!



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFilterDialogBinding.bind(view)




    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

}