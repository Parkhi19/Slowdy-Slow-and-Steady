package com.slowAndSteady.slowdy.view.receiver
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.slowAndSteady.slowdy.R



class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            createNotificationChannel(it)
            val builder = NotificationCompat.Builder(it, "channelId")
                .setSmallIcon(R.drawable.selected_icon)
                .setContentTitle("Habit Reminder")
                .setContentText("Don't forget to mark your habit")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(null)
            val notificationId = 1

            val notificationManager = NotificationManagerCompat.from(it)


            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.notify(notificationId, builder.build())
        }
    }
}

private fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Habit Reminder"
        val descriptionText = "Don't forget to mark your habit"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channelId", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}






