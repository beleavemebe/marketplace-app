package com.github.beleavemebe.ui.cart.di

import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.github.beleavemebe.ui.cart.CartViewModel
import com.github.beleavemebe.ui.cart.R
import com.github.beleavemebe.ui.cart.checkout.CheckoutForegroundWorker
import com.github.beleavemebe.ui.cart.checkout.CheckoutViewModel
import com.github.beleavemebe.ui.cart.checkout.OrderConstants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import java.util.UUID
import com.narcissus.marketplace.core.R as CORE

val cartModule = module {
    viewModel { CartViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { CheckoutViewModel(get(), get(), get()) }

    factory {
        WorkManager.getInstance(androidContext())
    }

    worker {
        CheckoutForegroundWorker(
            appContext = androidContext(),
            makeAnOrder = get(),
            getSelectedCartItems = get(),
            restoreCartItems = get(),
            params = get(),
        )
    }

    factory(
        qualifier<CartQualifiers.PaymentWorkerInputData>()
    ) { (orderUUID: String) ->
        Data.Builder()
            .putString(OrderConstants.KEY_ORDER_UUID, orderUUID)
            .putInt(OrderConstants.KEY_ON_COMPLETE_NOTIFICATION_ID, UUID.randomUUID().toString().hashCode())
            .build()
    }

    factory(
        qualifier<CartQualifiers.PaymentOneTimeRequest>()
    ) { (data: Data) ->
        OneTimeWorkRequest.Builder(CheckoutForegroundWorker::class.java)
            .setConstraints(
                Constraints.Builder()
                    .build(),
            )
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data)
            .build()
    }

    factory(
        qualifier<CartQualifiers.PaymentInProgressNotification>()
    ) {
        NotificationCompat.Builder(androidContext(), OrderConstants.CHECKOUT_CHANNEL_ID)
            .setSmallIcon(CORE.drawable.ic_logo)
            .setOngoing(true)
            .setAutoCancel(true)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLocalOnly(true)
            .setContentText(androidContext().getString(R.string.please_wait))
            .build()
    }
}
