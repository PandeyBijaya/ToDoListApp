package com.firstapp.todolistapp;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
    String Title,year,month,day, hour, min;
    int id;

    @Override
    public void onReceive(Context context, Intent intent) {

        Title= intent.getStringExtra("title");
        year= intent.getStringExtra("year");
        month= intent.getStringExtra("month");
        day= intent.getStringExtra("day");
        hour= intent.getStringExtra("hour");
        min= intent.getStringExtra("min");
        id= intent.getIntExtra("id",0);


        Intent iApp= new Intent(context.getApplicationContext(), UpdateActivity.class);
        iApp.putExtra("title",Title);
        iApp.putExtra("year",year);
        iApp.putExtra("month",month);
        iApp.putExtra("day",day);
        iApp.putExtra("hour",hour);
        iApp.putExtra("min",min);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags = PendingIntent.FLAG_MUTABLE;
        }
        PendingIntent pi= PendingIntent.getActivity(context, id, iApp, flags);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             notification = new Notification.Builder(context, CHANNEL_ID)
                    .setContentText("DUE ENDED FOR "+Title)
                    .setSubText("Ye, times up! Did you complete your work?")
                     .setContentIntent(pi)
                    .setSmallIcon(R.drawable.baseline_access_time_24)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID, "TASK_NOTIFY", NotificationManager.IMPORTANCE_HIGH));

        }
        else{
            notification = new Notification.Builder(context)
                    .setContentText("DUE ENDED FOR YOUR TASK")
                    .setSubText("Ye, times up! Did you complete your work?")
                    .setSmallIcon(R.drawable.baseline_access_time_24)
                    .setContentIntent(pi)
                    .build();
        }


            nm.notify(NOTIFICATION_ID, notification);

        }


    }

