package com.narcissus.marketplace.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCartBinding


class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)
        initRecyclerView()
        initButtons()
        subscribeToViewModel()
    }

    private val adapter = ListDelegationAdapter(CartItems.ItemsList.delegate)

    private fun initRecyclerView() {
        binding.rvCartContent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCartContent.adapter = adapter
        adapter.items = listOf(CartItems())
    }


    private fun subscribeToViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                observeCartCost()
                observeItemsInCart()
                observeCart()
        }
    }

    private suspend fun observeCartCost() {
        viewModel.getCartCostFlow.collect { totalPrice ->
            binding.tvTotalPrice.text = totalPrice
        }
    }

    private suspend fun observeItemsInCart() {
        viewModel.getCartItemsAmountFlow.collect { amount ->
            binding.tvProductsAmount.text = amount
        }
    }

    private suspend fun observeCart() {
        viewModel.getCartFlow.collect { items ->
            adapter.items = adapter.items.modifiedAt(CartItems.ItemsList(items))
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun selectAll(isSelected:Boolean){
        viewModel.selectAll(isSelected).collect{
            adapter.items = adapter.items.modifiedAt(CartItems.ItemsList(it))
            adapter.notifyDataSetChanged()
        }
    }

    private fun initButtons(){
        binding.cbSelectAll.setOnCheckedChangeListener { compoundButton, _ ->
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                if (compoundButton.isChecked)
                    selectAll(true)
                else {
                    selectAll(false)
                }
            }
        }
        binding.btnDeleteSelected.setOnClickListener {

        }
    }


    private fun List<CartItems>.modifiedAt(
        with: CartItems, index:Int = 0
    ): List<CartItems> {
        val result = this.toMutableList()
        result[index] = with
        return result
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
