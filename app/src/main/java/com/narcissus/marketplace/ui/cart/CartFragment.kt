package com.narcissus.marketplace.ui.cart

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.transition.MaterialFadeThrough
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCartBinding
import com.narcissus.marketplace.domain.model.CartItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)
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
        object : AsyncListDifferDelegationAdapter<CartListItem>(
            CartListItem.DIFF_CALLBACK,
            CartListItem.Item.delegate(
                viewModel::deleteItem,
                viewModel::onItemChecked,
                viewModel::onItemAmountChanged,
                viewLifecycleOwner.lifecycleScope,
            ),
        ) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough()
    }

    private fun initRecyclerView() {
        binding.rvCartContent.adapter = adapter
    }

    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            observeCartCost()
            observeCartItemAmount()
            observeCart()
        }
    }

    private fun observeCartCost() {
        viewModel.getCartCostFlow.onEach { totalPrice ->
            binding.tvTotalPrice.text = totalPrice
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeCartItemAmount() {
        viewModel.getCartItemsAmountFlow.onEach { amount ->
            binding.tvProductsAmount.text = amount
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeCart() {
        viewModel.getCartFlow.onEach { items ->
            val isNotEmpty = items.isNotEmpty()
            binding.checkoutBar.isVisible = isNotEmpty
            binding.nsvCartContent.isVisible = isNotEmpty
            binding.selectDeleteBar.isVisible = isNotEmpty
            binding.clCartEmpty.isVisible = !isNotEmpty
            adapter.items = items.map { cartItem ->
                cartItem.toCartListItem()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun CartItem.toCartListItem() = CartListItem.Item(this)

    private fun initButtons() {
        binding.cbSelectAll.setOnCheckedChangeListener { _, isChecked ->
            viewModel.selectAll(isChecked)
        }

        binding.btnDeleteSelected.setOnClickListener {
            viewModel.deleteSelectedItems()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
