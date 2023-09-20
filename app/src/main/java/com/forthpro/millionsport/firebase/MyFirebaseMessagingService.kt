package com.forthpro.millionsport.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.forthpro.millionsport.R
import com.forthpro.millionsport.config.PreferenceHelper
import com.forthpro.millionsport.ui.home.HomeActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


open class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {

        private const val TAG = "MyFirebaseMsgService"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun handleIntent(intent: Intent) {
        try {
            if (intent.extras != null) {
                val builder = RemoteMessage.Builder("MyFirebaseMessagingService")
                for (key in intent.extras!!.keySet()) {
                    builder.addData(key!!, intent.extras!![key].toString())
                }
                onMessageReceived(builder.build())
            } else {
                super.handleIntent(intent)
            }
        } catch (e: Exception) {
            super.handleIntent(intent)
        }
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            sendNotification(
                remoteMessage.data["title"]!!,
                remoteMessage.data["message"]!!)

        } else {
            sendNotification(
                remoteMessage.notification!!.title, remoteMessage.notification!!.body
            )
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
//            sendNotification(it.body.toString(),"","","","")
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
        super.onNewToken(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.i(TAG, "Firebase Token: $token")
        PreferenceHelper.deviceToken = token!!
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(
        title: String?,
        message: String?,
        type: String?){

        var intent: Intent? = Intent(this, HomeActivity::class.java)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent, PendingIntent.FLAG_MUTABLE
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        notificationBuilder.setSmallIcon(R.mipmap.app_logo).setContentTitle(title)
            .setContentText(message).setAutoCancel(true).setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

    private fun sendNotification(
        title: String?,
        message: String?,
    ) {
        var intent: Intent? = Intent(this, HomeActivity::class.java)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this,0, intent, PendingIntent.FLAG_MUTABLE)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        notificationBuilder.setSmallIcon(R.mipmap.app_logo).setContentTitle(title)
            .setContentText(message).setAutoCancel(true).setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)       }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}