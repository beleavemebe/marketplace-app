package com.narcissus.marketplace.ui.cart.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.narcissus.marketplace.domain.model.orders.OrderPaymentStatus
import com.narcissus.marketplace.domain.usecase.GetSelectedCartItems
import com.narcissus.marketplace.domain.usecase.MakeAnOrder
import com.narcissus.marketplace.domain.usecase.RestoreCartItems
import com.narcissus.marketplace.ui.cart.R
import com.narcissus.marketplace.ui.cart.di.CartQualifiers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.qualifier
import com.narcissus.marketplace.core.R as CORE

class CheckoutForegroundWorker(
    private val appContext: Context,
    params: WorkerParameters,
    private val makeAnOrder: MakeAnOrder,
    private val getSelectedCartItems: GetSelectedCartItems,
    private val restoreCartItems: RestoreCartItems,
) : CoroutineWorker(appContext, params), KoinComponent {
    private val orderUUID = inputData.getString(OrderConstants.KEY_ORDER_UUID)

    private val paymentInProgressNotificationId = orderUUID.hashCode()
    private val paymentInProgressNotification: Notification by inject(
        qualifier<CartQualifiers.PaymentInProgressNotification>(),
    )

    private val onCompleteNotificationId = inputData.getInt(
        OrderConstants.KEY_ON_COMPLETE_NOTIFICATION_ID, 0,
    )

    private var isCheckoutFragmentDead = false
    private val isCheckoutFragmentDeadReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            intent
                ?.getBooleanExtra(orderUUID, false)
                ?.let { b ->
                    isCheckoutFragmentDead = b
                }
        }
    }

    override suspend fun doWork(): Result {
        val orderItems = getSelectedCartItems()
        return if (orderUUID == null) {
            Result.failure(workDataOf("0" to "Missing order id"))
        } else try {
            appContext.registerReceiver(
                isCheckoutFragmentDeadReceiver,
                IntentFilter(OrderConstants.PAY_INTENT_FILTER)
            )

            val orderResult = makeAnOrder(orderItems, orderUUID)

            if (orderResult.status == OrderPaymentStatus.PAID) {
                if (isCheckoutFragmentDead) {
                    showOnCompleteNotification(
                        true,
                        onCompleteNotificationId,
                        orderResult.message,
                        orderResult.number,
                    )
                }

                Result.success(workDataOf(orderUUID to orderResult.message))
            } else {
                if (isCheckoutFragmentDead) {
                    showOnCompleteNotification(
                        false,
                        onCompleteNotificationId,
                        orderResult.message,
                    )
                }

                restoreCartItems(orderItems)
                Result.failure(workDataOf(orderUUID to orderResult.message))
            }
        } catch (e: Exception) {
            restoreCartItems(orderItems)
            return Result.failure(workDataOf("0" to e.message))
        } finally {
            appContext.unregisterReceiver(isCheckoutFragmentDeadReceiver)
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createCheckoutNotificationChannel()
        }

        return ForegroundInfo(paymentInProgressNotificationId, paymentInProgressNotification)
    }

    private fun showOnCompleteNotification(
        success: Boolean,
        notificationId: Int,
        message: String,
        orderNumber: Int? = null,
    ) {
        val notification =
            NotificationCompat.Builder(
                appContext,
                OrderConstants.CHECKOUT_CHANNEL_ID,
            )
                .setSmallIcon(CORE.drawable.ic_logo)
                .setOngoing(false)
                .setStyle(NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentTitle(
                    if (success) {
                        appContext.getString(R.string.payment_successful)
                    } else {
                        appContext.getString(R.string.payment_failed)
                    }
                )
                .setContentText(
                    if (success) {
                        appContext.getString(R.string.order_is_paid_successfully, orderNumber)
                    } else {
                        message
                    }
                )
                .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createCheckoutNotificationChannel()
        }

        NotificationManagerCompat.from(appContext)
            .notify(notificationId, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createCheckoutNotificationChannel() {
        val notificationChannel =
            NotificationChannel(
                OrderConstants.CHECKOUT_CHANNEL_ID,
                appContext.getString(R.string.checkout_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            )

        NotificationManagerCompat.from(appContext)
            .createNotificationChannel(notificationChannel)
    }
}
