package com.narcissus.marketplace.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.narcissus.marketplace.R
import com.narcissus.marketplace.databinding.FragmentCheckoutBinding
import com.narcissus.marketplace.di.NotificationQualifiers
import com.narcissus.marketplace.domain.model.CheckoutItem
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.qualifier
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.util.UUID

class CheckoutFragment : BottomSheetDialogFragment(), KoinComponent {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CheckoutViewModel by viewModel()
    private val orderUUID = UUID.randomUUID().toString()

    //    val data = Data.Builder().putString(OrderConsts.ORDER_UUID_KEY, orderUUID)
//        .putString(OrderConsts.NOTIFICATION_ID_KEY, UUID.randomUUID().toString())
//        .putString(OrderConsts.RESULT_KEY, UUID.randomUUID().toString()).build()
    val data: Data by inject(qualifier<NotificationQualifiers.PaymentInputDataBuilder>()) {
        parametersOf(orderUUID)
    }
    private val paymentWorkRequest: OneTimeWorkRequest by inject(qualifier<NotificationQualifiers.PaymentRequestBuilder>()) {
        parametersOf(data)
    }

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
        subscribeToViewModel()
        watchEditText()
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
    }

    private fun observeCheckout() {
        viewModel.checkoutFlow.onEach { items ->
            adapter.items = items.map { checkoutItem ->
                checkoutItem.toCheckoutListItem()
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeTotalCost() {
        viewModel.totalCostFlow.onEach { total ->
            binding.tvOrderTotalPrice.text = context?.getString(
                R.string.price_placeholder, total,
            )
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun watchEditText() {
        watchCardNumberEditText()
        watchCardMonthYearEditText()
    }

    private fun watchCardMonthYearEditText() {
        val slots = UnderscoreDigitSlotsParser().parseSlots(MASK_MONTH_YEAR)
        MaskFormatWatcher(MaskImpl.createTerminated(slots)).installOn(binding.etMonthYear)
    }

    private fun watchCardNumberEditText() {
        val maskCardNumber = MaskImpl.createTerminated(PredefinedSlots.CARD_NUMBER_STANDARD)
        MaskFormatWatcher(maskCardNumber).installOn(binding.etCardNumber)
    }

    private fun initPlaceOrderButton() {
        binding.btnPlaceOrder.setOnClickListener {
         makeAnOrder()
        }
    }
    private fun makeAnOrder(){
        val workManager = WorkManager.getInstance(requireContext())
        workManager.getWorkInfoByIdLiveData(paymentWorkRequest.id)
            .observe(this) { workInfo: WorkInfo? ->
                if (workInfo != null) {
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> workInfo.outputData.getString(orderUUID)
                            ?.let { Log.d("DEBUG", "WORKER NUMBER: $it SUCCESS") }
                        WorkInfo.State.FAILED -> workInfo.outputData.getString(orderUUID)
                            ?.let { Log.d("DEBUG", "WORKER FAILED: $it") }
                        WorkInfo.State.RUNNING ->
                            Log.d(
                                "DEBUG",
                                "WORKER RUNNING",
                            )
                        else -> {
                            Log.d(
                                "DEBUG",
                                "SOMETHING WENT WRONG",
                            )
                        }
                    }
                }
                else Log.d(
                    "DEBUG",
                    "OH SHIIT",
                )
            }
        workManager.enqueue(paymentWorkRequest)
    }

    private fun CheckoutItem.toCheckoutListItem() = CheckoutListItem.Detail(this)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        requireContext().sendBroadcast(
            Intent(OrderConsts.PAY_INTENT_FILTER).putExtra(
                orderUUID,
                true,
            ),
        )
    }

    private companion object {
        const val MASK_MONTH_YEAR = "__/__"
    }
}
