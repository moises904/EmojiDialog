package com.demo.emojinotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    private var notificationManager: NotificationManager? = null
    private var messageEmoji: MessageEmoji? = null

    companion object {
        const val FILE_NAME_DATA = "custom_message.json"
        const val NAME_CHANEL    = "channelNotification"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializateView()
        prepareMessageData()
        createNotificationChannel(NAME_CHANEL)
        generateCustomNotification()

    }

    private fun initializateView() {
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    private fun prepareMessageData() {
        val dataMessageJson = getReadJsonData(applicationContext, FILE_NAME_DATA)
        dataMessageJson?.let {
            messageEmoji = parserMessage(it)
            messageEmoji?.let {
                    it1-> tvLink.setOnClickListener { showMessage(it1) }
            }
        }
    }

    private fun generateCustomNotification() {
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val notificationBuilder = NotificationCompat.Builder(this, NAME_CHANEL)
                                                    .setSmallIcon(android.R.drawable.ic_dialog_alert)
                                                    .setContentTitle("Title: Api " + Build.VERSION.SDK_INT)
                                                    .setContentText("UUID: " + UUID.randomUUID())
                                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                    .setContentIntent(pendingIntent)
                                                    .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(1, notificationBuilder.build())
        }

    }

    private fun createNotificationChannel(channelId: String) {
        // Create the NotificationChannel, but only on API 26+ (Android 8.0) because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelDescription = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(channelId, NAME_CHANEL, importance)
            channel.apply {
                description = channelDescription
            }
            // Finally register the channel with system
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showMessage(messageEmoji: MessageEmoji) {
       val customDialog = DialogWithEmoji(this, messageEmoji)
        customDialog.show()
    }

    private fun parserMessage(dataJson: String): MessageEmoji? {
        val gson = GsonBuilder().create()
        return  gson.fromJson(dataJson, MessageEmoji::class.java)
    }

    private fun getReadJsonData(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}
