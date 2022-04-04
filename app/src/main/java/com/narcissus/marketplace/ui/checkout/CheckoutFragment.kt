package com.narcissus.marketplace.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCheckoutBinding
import com.narcissus.marketplace.domain.model.CheckoutItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class CheckoutFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckoutViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCheckoutBinding.bind(inflater.inflate(R.layout.fragment_checkout, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCheckoutDetails.adapter = adapter
        subscribeToViewModel()
    }

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            CheckoutListItem.DIFF_CALLBACK,
            CheckoutListItem.Detail.delegate(),
        )
    }

    private fun subscribeToViewModel() {
        observeCheckout()
        observeTotalCost()
    }

    private fun observeCheckout() {
        viewModel.getCheckoutFlow.onEach { items ->
            adapter.items = items.map { checkoutItem ->
                checkoutItem.toCheckoutListItem()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeTotalCost() {
        viewModel.getTotalFlow.onEach { total ->
            binding.tvOrderTotalPrice.text = context?.getString(
                R.string.price_placeholder, total,
            )
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun CheckoutItem.toCheckoutListItem() = CheckoutListItem.Detail(this)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
