package com.narcissus.marketplace.ui.checkout.di

import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.narcissus.marketplace.R
import com.narcissus.marketplace.ui.checkout.CheckoutForegroundWorker
import com.narcissus.marketplace.ui.checkout.OrderConstants
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import java.util.UUID

val checkoutModule = module {
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
        qualifier<CheckoutQualifiers.PaymentWorkerInputData>()
    ) { (orderUUID: String) ->
        Data.Builder()
            .putString(OrderConstants.KEY_ORDER_UUID, orderUUID)
            .putInt(OrderConstants.KEY_ON_COMPLETE_NOTIFICATION_ID, UUID.randomUUID().toString().hashCode())
            .build()
    }

    factory(
        qualifier<CheckoutQualifiers.PaymentOneTimeRequest>()
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
        qualifier<CheckoutQualifiers.PaymentInProgressNotification>()
    ) {
        NotificationCompat.Builder(androidContext(), OrderConstants.CHECKOUT_CHANNEL_ID)
            .setSmallIcon(com.narcissus.marketplace.ui.splash.R.drawable.ic_logo)
            .setOngoing(true)
            .setAutoCancel(true)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLocalOnly(true)
            .setContentText(androidContext().getString(R.string.please_wait))
            .build()
    }
}
