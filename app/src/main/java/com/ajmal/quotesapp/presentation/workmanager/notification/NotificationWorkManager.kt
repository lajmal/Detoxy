package com.ajmal.quotesapp.presentation.workmanager.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ajmal.quotesapp.R
import com.ajmal.quotesapp.domain.usecases.home_screen_usecases.QuoteUseCase
import com.ajmal.quotesapp.presentation.MainActivity
import com.ajmal.quotesapp.util.Constants
import com.ajmal.quotesapp.util.NOTIFICATION_AUTHOR_KEY
import com.ajmal.quotesapp.util.NOTIFICATION_QUOTE_KEY
import com.ajmal.quotesapp.util.Resource
import com.ajmal.quotesapp.util.dataStore
import com.ajmal.quotesapp.util.saveNotificationQuote

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull

@HiltWorker
class NotificationWorkManager @AssistedInject constructor(
    @Assisted private val  context: Context,
    @Assisted private val params: WorkerParameters,
    private val quoteUseCase: QuoteUseCase
) : CoroutineWorker(context, params)
{

    override suspend fun doWork(): Result {

        return try {

            Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "Work started")

            val response = fetchQuotes(context)

            if (!response){
                return Result.retry()
            }


            val savedQuote = context.dataStore.data.first()[NOTIFICATION_QUOTE_KEY] ?: "No quote saved yet!!!"
            val savedAuthor= context.dataStore.data.first()[NOTIFICATION_AUTHOR_KEY] ?: "No quote saved yet!!!"

            Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "Saved Quote in DataStore work manager: $savedQuote")

            createNotificationChannel()
            createNotification(context,savedQuote,savedAuthor)

            return Result.success()
        }
        catch (e: Exception) {
            Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "Exception in doWork", e)
            Result.failure()
        }

    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "Reminder Notifications"
            val descriptionText = "Channel for vital logging reminders"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
//                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }

            val notificationWorkManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationWorkManager.createNotificationChannel(channel)

        }

    }

    private fun createNotification(context: Context, quote: String, author: String) {

        val notificationLayout = RemoteViews(context.packageName, R.layout.notification_view)

        notificationLayout.setTextViewText(R.id.nv_title, quote)
        notificationLayout.setTextViewText(R.id.nv_author, author)

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon_black)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomBigContentView(notificationLayout)
            .setCustomContentView(notificationLayout)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
//            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(applicationContext,
                Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

            NotificationManagerCompat.from(applicationContext).notify(Constants.NOTIFICATION_ID, notification)
        }

    }

    private suspend fun fetchQuotes(context: Context):Boolean {

        return try {

            Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "inside fetchQuotes")

            val response =
                withTimeoutOrNull(5000) {
                    quoteUseCase.getQuote()
                        .filter { it is Resource.Success || it is Resource.Error }
                        .first()
                }
            when(response){

                is Resource.Success->{
                    val quote =  response.data?.quotesList?.getOrNull(1)

                    if(quote!=null){
                        Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "Fetched Quote: $quote")
                        context.saveNotificationQuote(quote.quote, quote.author)
                        true
                    }else{
                        Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "Quote is null")
                        false
                    }

                }
                is Resource.Error-> {
                    Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "Error from fetchQuotes: ${response.message}")
                    false
                }
                else -> {
                    Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "No response from fetchQuotes")
                    false
                }
            }

        } catch (e: Exception){
            Log.d(Constants.WORK_MANAGER_STATUS_NOTIFY, "Exception in fetchQuotes", e)
            false
        }

    }

    @AssistedFactory
    interface Factory {
        fun create(context: Context, params: WorkerParameters): NotificationWorkManager
    }

}