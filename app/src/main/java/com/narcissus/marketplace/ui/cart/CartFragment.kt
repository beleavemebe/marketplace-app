package com.narcissus.marketplace.ui.cart

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.transition.MaterialFadeThrough
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.core.databinding.LayoutProgressBarBinding
import com.narcissus.marketplace.core.util.launchWhenStarted
import com.narcissus.marketplace.databinding.FragmentCartBinding
import com.narcissus.marketplace.domain.model.CartItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.narcissus.marketplace.core.R as CORE

class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModel()
    private lateinit var dialog:AlertDialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)
        dialog = AlertDialog.Builder(
            context,
            com.narcissus.marketplace.core.R.style.LoadingDialogImpassable,
        )
            .setView(com.narcissus.marketplace.core.R.layout.layout_progress_bar).setCancelable(false).create()
        dialog.window?.setWindowAnimations(com.narcissus.marketplace.core.R.style.AA)
        renderTransition()
        initRecyclerView()
        initButtons()
        subscribeToViewModel()

    }

    private fun renderTransition() {
        postponeEnterTransition()
        binding.rvCartContent.post { startPostponedEnterTransition() }
    }

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            CartListItem.DIFF_CALLBACK,
            CartListItem.Item.delegate(
                viewModel::deleteItem,
                viewModel::onItemChecked,
                viewModel::onItemAmountChanged,
                viewLifecycleOwner.lifecycleScope,
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    private fun initRecyclerView() {
        binding.rvCartContent.adapter = adapter
        binding.rvCartContent.itemAnimator = null
    }

    private fun subscribeToViewModel() {
        observeCartCost()
        observeCartItemAmount()
        observeCart()
        observeAreAllItemsSelected()
        observeSelectedItems()
        observeLoadingState()
    }

    private fun observeLoadingState() {
        viewModel.loadingFlow.onEach { isActive->
            Log.d("DEBUG","view observed: $isActive")
            if(isActive) showLoadingProgressBar()
            else hideLoadingProgressBar()
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun showLoadingProgressBar() {
        Log.d("DEBUG","SHOWED")
        dialog.show()
    }
    private fun hideLoadingProgressBar(){
        Log.d("DEBUG","HIDED")
        dialog.dismiss()
    }

    private fun observeCartCost() {
        viewModel.cartCostFlow.onEach { totalPrice ->
            binding.tvTotalPrice.text = requireContext()
                .getString(
                    CORE.string.price_placeholder, totalPrice
                )
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeCartItemAmount() {
        viewModel.itemAmountFlow.onEach { amount ->
            binding.tvProductsAmount.text = requireContext()
                .getString(R.string.products_amount_placeholder, amount)
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeCart() {
        viewModel.cartFlow.onEach { items ->
            val isNotEmpty = items.isNotEmpty()
            binding.checkoutBar.isVisible = isNotEmpty
            binding.nsvCartContent.isVisible = isNotEmpty
            binding.selectDeleteBar.isVisible = isNotEmpty
            binding.clCartEmpty.isVisible = !isNotEmpty
            adapter.items = items.map { cartItem ->
                cartItem.toCartListItem()
            }
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }


    private fun observeAreAllItemsSelected() {
        viewModel.isSelectAllCheckboxActive.onEach { flag ->
            binding.cbSelectAll.setOnCheckedChangeListener(null)
            binding.cbSelectAll.isChecked = flag
            initSelectAllCheckbox()
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun CartItem.toCartListItem() = CartListItem.Item(this)

    private fun initButtons() {
        initSelectAllCheckbox()
        initDeleteSelectedButton()
        initCheckOutListeners()
    }

    private fun initCheckOutListeners() {
        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartToCheckout())
        }
    }

    private fun initSelectAllCheckbox() {
        binding.cbSelectAll.setOnCheckedChangeListener { _, isChecked ->
            if(binding.cbSelectAll.isPressed)
                viewModel.selectAll(isChecked)
        }
    }

    private fun observeSelectedItems() {
        viewModel.isCheckoutButtonActive.onEach { selectedItem ->
            binding.btnCheckout.isEnabled = selectedItem
        }.launchWhenStarted(viewLifecycleOwner.lifecycleScope)
    }

    private fun initDeleteSelectedButton() {
        binding.btnDeleteSelected.setOnClickListener {
            viewModel.deleteSelectedItems()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
