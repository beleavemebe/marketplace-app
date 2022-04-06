package com.narcissus.marketplace.ui.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.narcissus.marketplace.R
import com.narcissus.marketplace.di.NotificationQualifiers
import com.narcissus.marketplace.di.OrderWorkerIds
import com.narcissus.marketplace.domain.model.OrderPaymentStatus
import com.narcissus.marketplace.domain.usecase.GetCart
import com.narcissus.marketplace.domain.usecase.MakeAnOrder
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.qualifier
import java.lang.Exception
import java.util.UUID

class CheckoutForegroundWorker(
    private val appContext: Context,
    params: WorkerParameters,
) : CoroutineWorker(appContext, params), KoinComponent {
    private val makeAnOrder: MakeAnOrder by inject()
    private val getCart: GetCart by inject()
    private val processNotificationId = UUID.randomUUID().hashCode()
    private var isFragmentViewDestroyed: Boolean = false
    private var orderUUID: String? = null
    private val processNotification: Notification by inject(qualifier<NotificationQualifiers.ProcessPaymentNotification>())

    @RequiresApi(Build.VERSION_CODES.O)
    private val notificationChannel =
        NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
    private val notificationManager =
        appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        orderUUID = inputData.getString(GET_ORDER_UUID_KEY)
        val finalNotificationId = inputData.getInt(GET_NOTIFICATION_ID_KEY, 0)
        val resultKey = inputData.getString(GET_RESULT_KEY)
        if (orderUUID == null || finalNotificationId == 0 || resultKey == null) {
            Result.failure(workDataOf(Pair(resultKey ?: "0", "Worker initialize error")))
        }
        appContext.registerReceiver(
            fragmentViewStateReceiver,
            IntentFilter(PayIntentFilter.PAY_INTENT_FILTER),
        )
        //ошибочки
        val orderResult = makeAnOrder(getCart().first(), orderUUID!!)

        return if (orderResult.status == OrderPaymentStatus.PAID) {
            if (isFragmentViewDestroyed) showFinallyNotification(
                true,
                finalNotificationId,
                orderResult.message,
            )
            Result.success(workDataOf(Pair(resultKey!!, orderResult.message)))
        } else {
            Log.d("DEBUG","DOSHLI")
            if (isFragmentViewDestroyed) showFinallyNotification(
                false,
                finalNotificationId,
                orderResult.message,
            )
            Result.failure(workDataOf(Pair(resultKey!!, orderResult.message)))
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel)
        }
        return ForegroundInfo(processNotificationId, processNotification)
    }

    private fun showFinallyNotification(
        isSuccess: Boolean,
        notificationId: Int,
        message: String,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel)
            val notification =
                NotificationCompat.Builder(
                    appContext,
                    OrderWorkerIds.NOTIFICATION_PROCESS_CHANEL_ID,
                )
                    .setSmallIcon(com.narcissus.marketplace.ui.splash.R.drawable.ic_logo)
                    .setOngoing(false)
                    .setAutoCancel(true).apply {
                        when (isSuccess) {
                            true -> setContentTitle(appContext.getString(R.string.success))
                                .setContentText(appContext.getString(R.string.your_order_has_been_paid))
                            false -> setContentTitle("ERROR")
                                .setContentText(message)
                        }
                    }.build()
            notificationManager.notify(notificationId, notification)
        }

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
        const val CHANNEL_NAME = "MarketplaceNotificationChannelName"
        const val GET_NOTIFICATION_ID_KEY = "NotificationId"
        const val GET_RESULT_KEY = "ResultKey"
        const val GET_ORDER_UUID_KEY = "Order UUID"
    }

}

