package com.firstapp.todolistapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;

public class NotificationHelper {

    public static final String CHANNEL_ID = "TASK_CHANNEL";
    public static final String CHANNEL_NAME = "Task Notifications";
    public static final String CHANNEL_DESCRIPTION = "Notification channel for task reminders";
    public static AlarmManager alarmManager;
    public static Context context;

    // Method to create notification channel

    public void scheduleNotif(Context newContext, TaskData data){

        if(context==null)
            context= newContext;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(data.getHour()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(data.getMin()));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.YEAR, Integer.parseInt(data.getYear()));
        calendar.set(Calendar.MONTH, Integer.parseInt(data.getMonth()));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(data.getDay()));

        long delay = calendar.getTimeInMillis();


        Intent iBroadCast = new Intent(context, NotificationReceiver.class);
        iBroadCast.putExtra("title",data.getTitle());
        iBroadCast.putExtra("year",data.getYear());
        iBroadCast.putExtra("month",data.getMonth());
        iBroadCast.putExtra("day",data.getDay());
        iBroadCast.putExtra("hour",data.getHour());
        iBroadCast.putExtra("min", data.getMin());
        iBroadCast.putExtra("id",data.getId());



        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags |= PendingIntent.FLAG_MUTABLE;
        }
        PendingIntent pi = PendingIntent.getBroadcast(context, data.getId(), iBroadCast, flags);

        if(alarmManager== null)
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pi);
    }
    public void cancelNotif( Context newContext, int id)
    {
        if(context==null)
            context= newContext;

        if(alarmManager==null)
            alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent iBroadCast= new Intent(context, NotificationReceiver.class);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            flags = PendingIntent.FLAG_MUTABLE;
        }
        PendingIntent pi = PendingIntent.getBroadcast(context, id, iBroadCast, flags);
        alarmManager.cancel(pi);
        pi.cancel();
    }
}
