package com.firstapp.todolistapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String CHANNEL_ID = "TASK_CHANNEL";
    private static final int NOTIFICATION_ID = 159;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "yess", Toast.LENGTH_SHORT).show();

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             notification = new Notification.Builder(context, CHANNEL_ID)
                    .setContentText("DUE ENDED FOR YOUR TASK")
                    .setSubText("Ye, times up! Did you complete your work?")
                    .setSmallIcon(R.drawable.baseline_access_time_24)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "TASK_NOTIFY", NotificationManager.IMPORTANCE_HIGH));

        }
        else{
            notification = new Notification.Builder(context)
                    .setContentText("DUE ENDED FOR YOUR TASK")
                    .setSubText("Ye, times up! Did you complete your work?")
                    .setSmallIcon(R.drawable.baseline_access_time_24)
                    .build();
        }


            nm.notify(NOTIFICATION_ID, notification);

        }


    }

