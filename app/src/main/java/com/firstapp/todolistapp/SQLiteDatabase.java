package com.firstapp.todolistapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class SQLiteDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "toDoList.db";
    private static final String TABLE_NAME="Tasks";
    private static final String ID="ID";
    private static final String COLUMN_TASK="tesk";
    private static final String COLUMN_DAYSLEFT="daysleft";
    private static final String COLUMN_YEAR="year";
    private static final String COLUMN_MONTH="month";
    private static final String COLUMN_DAYS="days";
    private static final String COLUMN_HOUR="hour";
    private static final String COLUMN_MIN="min";
    Context context;



    ArrayList<DataModel> arrList= new ArrayList<>();


    public SQLiteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+ID+" Integer PRIMARY KEY AUTOINCREMENT,"+COLUMN_TASK+" TEXT, "+COLUMN_YEAR+" INTEGER, "+COLUMN_MONTH+ " INTEGER, "+COLUMN_DAYSLEFT+" INTEGER, "+COLUMN_DAYS+" INTEGER," + COLUMN_HOUR+" Integer,"+ COLUMN_MIN+" Integer)");

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);


    }
    public void insertData(String Title, int year, int month, int day, int daysleft, int hour, int min) {


        android.database.sqlite.SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, Title);
        values.put(COLUMN_YEAR, year);
        values.put(COLUMN_MONTH, month);
        values.put(COLUMN_DAYS, day);
        values.put(COLUMN_DAYSLEFT, daysleft);
        values.put(COLUMN_HOUR, hour);
        values.put(COLUMN_MIN, min);


        db.insert(TABLE_NAME, null, values);


        //WORKING TO SET UP NOTIFICATION
        NotificationHelper notificationHelper= new NotificationHelper();

        notificationHelper.scheduleNotif(context, Title, year, month, day, hour, min, getId(Title));
    }

    public ArrayList<DataModel> fetchData()
    {


            android.database.sqlite.SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +" ORDER BY "+ COLUMN_DAYSLEFT+" ASC", null);




        while (cursor.moveToNext()) {

                DataModel dataModel = new DataModel();
                dataModel.title = cursor.getString(1);
                dataModel.year = Integer.toString(cursor.getInt(2));
                dataModel.month = Integer.toString(cursor.getInt(3));
                dataModel.id = cursor.getInt(4);
                dataModel.day = Integer.toString(cursor.getInt(5));

                dataModel.hour = dataModel.min = Integer.toString(cursor.getInt(6));
                dataModel.min = Integer.toString(cursor.getInt(7));
                arrList.add(dataModel);

            }
            cursor.close();
            db.close();



        return arrList;
    }

    public void deleteTask(String title)
    {
        android.database.sqlite.SQLiteDatabase db= getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_TASK+"= ?", new String[]{title});
        db.close();
        int id= getId(title);

        //Deleting the notification that was set up
        NotificationHelper notificationHelper= new NotificationHelper();
        notificationHelper.cancelNotif(context, getId(title));
    }

    public void updateTask(int id, String newTitle, int year, int month, int day, int daysLeft, int hour, int min)
    {
        android.database.sqlite.SQLiteDatabase db= getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMN_TASK, newTitle);
        values.put(COLUMN_YEAR,year);
        values.put(COLUMN_MONTH,month);
        values.put(COLUMN_DAYS,day);
        values.put(COLUMN_DAYSLEFT,daysLeft);
        values.put(COLUMN_HOUR,hour);
        values.put(COLUMN_MIN,min);

        db.update(TABLE_NAME, values, ID+"= ?", new String[]{Integer.toString(id)});


//Updating the notification that was set up
        NotificationHelper notificationHelper= new NotificationHelper();

        notificationHelper.cancelNotif(context, id);
        notificationHelper.scheduleNotif(context, newTitle, year, month, day, hour, min, getId(newTitle));
        db.close();
    }






    public int getId(String title) {
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_TASK + "=?", new String[]{title});
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }
    public String getTitle(int i) {
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_TASK + " FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(i)});
        String id = " ";
        while (cursor.moveToNext()) {
            id = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return id;
    }
    public int getYear(int i) {
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_YEAR + " FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(i)});
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }
    public int getMonth(int i) {
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_MONTH + " FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(i)});
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }
    public int getDay(int i) {
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DAYS + " FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(i)});
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }
    public int getHour(int i) {
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_HOUR + " FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(i)});
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }
    public int getMinute(int i) {
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_MIN + " FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(i)});
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }
    public int getDaysLeft(int i) {
        android.database.sqlite.SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_DAYSLEFT + " FROM " + TABLE_NAME + " WHERE " + ID + "=?", new String[]{String.valueOf(i)});
        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }

}
