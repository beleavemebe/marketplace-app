package com.narcissus.marketplace.di

import androidx.core.app.NotificationCompat
import com.narcissus.marketplace.R
import com.narcissus.marketplace.ui.checkout.CheckoutForegroundWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.named
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val workerModule = module {
    worker { CheckoutForegroundWorker(androidContext(), get()) }
    factory(qualifier<NotificationQualifiers.ProcessPaymentNotification>()) {
        NotificationCompat.Builder(androidContext(), OrderWorkerIds.NOTIFICATION_PROCESS_CHANEL_ID)
            .setSmallIcon(com.narcissus.marketplace.ui.splash.R.drawable.ic_logo)
            .setOngoing(true)
            .setAutoCancel(true)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setLocalOnly(true)
            .setContentText(androidContext().getString(R.string.please_wait))
            .build()
    }

}

object OrderWorkerIds {
    const val NOTIFICATION_PROCESS_CHANEL_ID = "MarketplaceChannelId"
}

object NotificationQualifiers {
    object ProcessPaymentNotification
}
