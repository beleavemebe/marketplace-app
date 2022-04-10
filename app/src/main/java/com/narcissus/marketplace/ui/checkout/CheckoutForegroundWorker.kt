package com.narcissus.marketplace.ui.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.narcissus.marketplace.R
import com.narcissus.marketplace.di.NotificationQualifiers
import com.narcissus.marketplace.di.OrderWorkerIds
import com.narcissus.marketplace.domain.model.orders.OrderPaymentStatus
import com.narcissus.marketplace.domain.usecase.GetCartSelectedItemsSnapshot
import com.narcissus.marketplace.domain.usecase.MakeAnOrder
import com.narcissus.marketplace.domain.usecase.RestoreCartItems
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.qualifier
import java.util.UUID

class CheckoutForegroundWorker(
    private val appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params), KoinComponent {
    private val makeAnOrder: MakeAnOrder by inject()
    private val getCartSelectedItemsSnapshot: GetCartSelectedItemsSnapshot by inject()
    private val restoreCartItems: RestoreCartItems by inject()
    private val processNotificationId = UUID.randomUUID().hashCode()
    private var isFragmentViewDestroyed: Boolean = false
    private var orderUUID: String? = null
    private val processNotification: Notification by inject(qualifier<NotificationQualifiers.ProcessPaymentNotification>())

    private val notificationManager = NotificationManagerCompat.from(appContext)


    override suspend fun doWork(): Result {
        val orderData = getCartSelectedItemsSnapshot()
        orderUUID = inputData.getString(OrderConsts.ORDER_UUID_KEY)
        val finalNotificationId = inputData.getInt(OrderConsts.NOTIFICATION_ID_KEY, 0)
        if (orderUUID == null || finalNotificationId == 0 || orderUUID == null) {
            Result.failure(workDataOf(Pair("0", "Worker initialize error")))
        }
        appContext.registerReceiver(
            fragmentViewStateReceiver,
            IntentFilter(OrderConsts.PAY_INTENT_FILTER),
        )

        try {
            val orderResult = makeAnOrder(orderData, orderUUID!!)
            return if (orderResult.status == OrderPaymentStatus.PAID) {
                if (isFragmentViewDestroyed) showFinallyNotification(
                    true,
                    finalNotificationId,
                    orderResult.message,
                )
                Result.success(workDataOf(Pair(orderUUID!!, orderResult.message)))
            } else {
                if (isFragmentViewDestroyed) showFinallyNotification(
                    false,
                    finalNotificationId,
                    orderResult.message,
                    orderResult.number
                )
                restoreCartItems(orderData)
                Result.failure(workDataOf(Pair(orderUUID!!, orderResult.message)))
            }

        } catch (e: Exception) {
            restoreCartItems(orderData)
            return Result.failure(workDataOf(Pair("0", e.message)))
        } finally {
            appContext.unregisterReceiver(fragmentViewStateReceiver)
        }

    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    appContext.getString(R.string.orders),
                    NotificationManager.IMPORTANCE_HIGH,
                )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return ForegroundInfo(processNotificationId, processNotification)
    }

    private fun showFinallyNotification(
        isSuccess: Boolean,
        notificationId: Int,
        message: String,
        orderNumber:Int? = null,
    ) {
        val notification =
            NotificationCompat.Builder(
                appContext,
                OrderWorkerIds.NOTIFICATION_PROCESS_CHANNEL_ID,
            )
                .setSmallIcon(com.narcissus.marketplace.ui.splash.R.drawable.ic_logo)
                .setOngoing(false)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true).apply {
                    when (isSuccess) {
                        true -> setContentTitle(appContext.getString(R.string.success))
                            .setContentText(appContext.getString(R.string.your_order_has_been_paid_placeholder,orderNumber))
                        false -> setContentTitle(appContext.getString(R.string.payment_failed))
                            .setContentText(message)
                    }
                }.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    CHANNEL_ID,
                    appContext.getString(R.string.orders),
                    NotificationManager.IMPORTANCE_HIGH,
                )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(notificationId, notification)


    }

    private val fragmentViewStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            intent?.getBooleanExtra(orderUUID, false)?.let {
                isFragmentViewDestroyed = it
            }
        }
    }

    companion object {
        const val CHANNEL_ID = "MarketplaceChannelId"
    }

}

