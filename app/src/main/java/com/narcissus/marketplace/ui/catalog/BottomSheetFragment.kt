package com.narcissus.marketplace.ui.catalog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentBottomSheetBinding


class BottomSheetFragment : BottomSheetDialogFragment() {

    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private var _binding: FragmentBottomSheetBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheet = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.fragment_bottom_sheet, null) as View
        bottomSheet.setContentView(view);
        val bottomSheetLayout =
            bottomSheet.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        if (bottomSheetLayout != null) {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        }

        bottomSheetBehavior?.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO;
        bottomSheetBehavior?.isFitToContents = false
        bottomSheetBehavior?.addBottomSheetCallback(mBottomSheetBehaviorCallback)
        return bottomSheet
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivBack.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private val mBottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (BottomSheetBehavior.STATE_EXPANDED == newState) {
                dismiss();
            }
            if (BottomSheetBehavior.STATE_HALF_EXPANDED == newState) {
                binding.animationView.visibility = View.VISIBLE
            }

            if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
            }

            if (BottomSheetBehavior.STATE_HIDDEN == newState) {
                dismiss();
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }
}