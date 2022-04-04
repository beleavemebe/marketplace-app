package com.narcissus.marketplace.ui.checkout

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.narcissus.marketplace.R
import com.narcissus.marketplace.domain.model.OrderPaymentResult
import com.narcissus.marketplace.domain.model.OrderPaymentStatus
import com.narcissus.marketplace.domain.usecase.GetCart
import com.narcissus.marketplace.domain.usecase.MakeAnOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class CheckoutForegroundWorker(
    private val appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params),KoinComponent {
    private val makeAnOrder:MakeAnOrder by inject()
    private val getCart:GetCart by inject()
    private val returnKey:String by inject(qualifier = named("checkout worker result key"))
    override suspend fun doWork(): Result {
        val orderResult = makeAnOrder(getCart().first())
        return if(orderResult.status==OrderPaymentStatus.PAID)
            Result.success()
        else Result.failure(workDataOf(Pair(returnKey,orderResult.message)))
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationManager =
            appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_MIN
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(appContext, CHANNEL_ID)
           // .setContentIntent(PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), Constants.PENDING_INTENT_FLAG_IMMUTABLE))
            .setSmallIcon(com.narcissus.marketplace.ui.splash.R.drawable.ic_logo)
            .setOngoing(true)
            .setAutoCancel(true)
            .setOnlyAlertOnce(false)
            .setSilent(true)
            .setPriority(NotificationCompat.PRIORITY_MIN)
            .setContentTitle(appContext.getString(R.string.process_your_order))
            .setLocalOnly(true)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .setContentText(appContext.getString(R.string.please_wait))
            .build()
        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    companion object {
        const val NOTIFICATION_ID = 42
        const val CHANNEL_ID = "MarketplaceChannelId"
        const val CHANNEL_NAME = "MarketplaceNotificationChannelName"
    }
}
