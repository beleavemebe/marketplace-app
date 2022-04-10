package com.narcissus.marketplace.ui.checkout

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCheckoutBinding
import com.narcissus.marketplace.domain.card.CardValidateResult
import com.narcissus.marketplace.domain.model.CheckoutItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class CheckoutFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckoutViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            FragmentCheckoutBinding.bind(inflater.inflate(R.layout.fragment_checkout, container))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCheckoutDetails.adapter = adapter
        watchEditText()
        subscribeToViewModel()
        initPlaceOrderButton()
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
        observeCardState()
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

    private fun errorState(cardState: CardValidateResult) {
        binding.btnPlaceOrder.isEnabled = false
        when (cardState) {
            is CardValidateResult.InvalidCardHolderName -> showCardHolderNameError()
            is CardValidateResult.InvalidCardNumber -> showCardNumberError()
            is CardValidateResult.InvalidExpireDate -> showCardExpireDateError()
            is CardValidateResult.InvalidCvv -> showCardCvvError()
        }
    }

    private fun observeCardState() {
        viewModel.cardValidateFlow.onEach { result ->
            when (result) {
                is CardValidateResult.Success -> cardValidated()
                else -> errorState(result)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun cardValidated() {
        clearErrorState()
        binding.btnPlaceOrder.isEnabled = true
    }

    private fun clearErrorState() {
        binding.etCardHolder.error = null
        binding.etCardNumber.error = null
        binding.etMonthYear.error = null
        binding.etCvv.error = null
    }

    private fun showCardHolderNameError() {
        binding.etCardHolder.error = getString(R.string.name_empty)

    }

    private fun showCardNumberError() {
        binding.etCardNumber.error = getString(R.string.invalid_card_number)
    }

    private fun showCardExpireDateError() {
        binding.etMonthYear.error = getString(R.string.invalid_expire_date)
    }

    private fun showCardCvvError() {
        binding.etCvv.error = getString(R.string.invalid_cvv)
    }

    private fun watchEditText() {
        binding.btnPlaceOrder.isEnabled = false
        watchCardHolderName()
        watchCardNumber()
        watchCardMonthYear()
        watchCardCvv()
    }

    private fun watchCardHolderName() {
        binding.etCardHolder.addTextChangedListener(textWatcher())
    }

    private fun watchCardNumber() {
        val maskCardNumber = MaskImpl.createTerminated(PredefinedSlots.CARD_NUMBER_STANDARD)
        MaskFormatWatcher(maskCardNumber).installOn(binding.etCardNumber)
        binding.etCardNumber.addTextChangedListener(textWatcher())
    }

    private fun watchCardMonthYear() {
        val slots = UnderscoreDigitSlotsParser().parseSlots(MASK_MONTH_YEAR)
        MaskFormatWatcher(MaskImpl.createTerminated(slots)).installOn(binding.etMonthYear)
        binding.etCardNumber.addTextChangedListener(textWatcher())
    }

    private fun watchCardCvv() {
        binding.etCvv.addTextChangedListener(textWatcher())
    }

    private fun textWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            checkCard()
        }
    }

    private fun checkCard() {
        viewModel.checkCard(
            binding.etCardHolder.text.toString(),
            binding.etCardNumber.text.toString(),
            binding.etMonthYear.text.toString(),
            binding.etCvv.text.toString(),
        )
    }

    private fun initPlaceOrderButton() {
        binding.btnPlaceOrder.setOnClickListener {
        }
    }

    private fun CheckoutItem.toCheckoutListItem() = CheckoutListItem.Detail(this)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        const val MASK_MONTH_YEAR = "__/__"
    }
}
