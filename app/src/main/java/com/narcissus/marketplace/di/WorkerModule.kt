package com.narcissus.marketplace.di

import androidx.core.app.NotificationCompat
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OutOfQuotaPolicy
import com.narcissus.marketplace.R
import com.narcissus.marketplace.ui.checkout.CheckoutForegroundWorker
import com.narcissus.marketplace.ui.checkout.OrderConsts
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import java.util.UUID

val workerModule = module {
    worker { CheckoutForegroundWorker(androidContext(), get()) }
    factory(qualifier<NotificationQualifiers.ProcessPaymentNotification>()) {
        NotificationCompat.Builder(androidContext(), OrderWorkerIds.NOTIFICATION_PROCESS_CHANNEL_ID)
            .setSmallIcon(com.narcissus.marketplace.ui.splash.R.drawable.ic_logo)
            .setOngoing(true)
            .setAutoCancel(true)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLocalOnly(true)
            .setContentText(androidContext().getString(R.string.please_wait))
            .build()
    }
    val constraints = Constraints.Builder()
        // .setRequiresStorageNotLow(true)
        .build()
    factory(qualifier<NotificationQualifiers.PaymentRequestBuilder>()) { (data: Data) ->
        OneTimeWorkRequest.Builder(CheckoutForegroundWorker::class.java)
            .setConstraints(constraints)
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(data).build()
    }
    factory(qualifier<NotificationQualifiers.PaymentInputDataBuilder>()) { (orderUUID: String) ->
        Data.Builder().putString(
            OrderConsts.ORDER_UUID_KEY, orderUUID
        )
            .putString(OrderConsts.NOTIFICATION_ID_KEY, UUID.randomUUID().toString())
            .putString(OrderConsts.RESULT_KEY, UUID.randomUUID().toString()).build()
    }
}

object OrderWorkerIds {
    const val NOTIFICATION_PROCESS_CHANNEL_ID = "MarketplaceChannelId"
}

object NotificationQualifiers {
    object ProcessPaymentNotification
    object PaymentRequestBuilder
    object PaymentInputDataBuilder
}
