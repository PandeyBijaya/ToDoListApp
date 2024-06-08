package com.firstapp.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SQLiteDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME= "TASKS.db";
    private static final String TABLE_NAME="toDoTask";
    private static final String ID="ID";
    private static final String COLUMN_TASK="tesk";
    private static final String COLUMN_DAYSLEFT="daysleft";
    private static final String COLUMN_YEAR="year";
    private static final String COLUMN_MONTH="month";
    private static final String COLUMN_DAYS="days";
    Context context;


    ArrayList<DataModel> arrList= new ArrayList<>();


    public SQLiteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+ID+" Integer PRIMARY KEY AUTOINCREMENT,"+COLUMN_TASK+" TEXT, "+COLUMN_YEAR+" INTEGER, "+COLUMN_MONTH+ " INTEGER, "+COLUMN_DAYSLEFT+" INTEGER, "+COLUMN_DAYS+" INTEGER)");

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }
    public void insertData(String Title, int year, int month, int day, int daysleft)
    {
        android.database.sqlite.SQLiteDatabase db= getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COLUMN_TASK, Title);
        values.put(COLUMN_YEAR,year);
        values.put(COLUMN_MONTH,month);
        values.put(COLUMN_DAYS,day);
        values.put(COLUMN_DAYSLEFT,daysleft);


        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<DataModel> fetchData()
    {
        android.database.sqlite.SQLiteDatabase db= getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);

        while (cursor.moveToNext())
        {
            DataModel dataModel= new DataModel();
            dataModel.title= cursor.getString(1);
            dataModel.year= Integer.toString(cursor.getInt(2));
            dataModel.month= Integer.toString(cursor.getInt(3));
            dataModel.id= cursor.getInt(4);
            dataModel.day= Integer.toString(cursor.getInt(5));

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
    }

    public void updateTask(int id, String title, int year, int month, int day, int daysLeft)
    {
        android.database.sqlite.SQLiteDatabase db= getWritableDatabase();

        ContentValues values= new ContentValues();
        values.put(COLUMN_TASK, title);
        values.put(COLUMN_YEAR,year);
        values.put(COLUMN_MONTH,month);
        values.put(COLUMN_DAYS,day);
        values.put(COLUMN_DAYSLEFT,daysLeft);

        db.update(TABLE_NAME, values, ID+"= "+ID, null);
    }

}
