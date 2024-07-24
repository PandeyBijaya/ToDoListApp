package com.firstapp.todolistapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "tasks")
public class TaskData {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name="title")
    String title;

    @ColumnInfo(name="year")
    String year;

    @ColumnInfo(name="month")
    String month;

    @ColumnInfo(name="day")
    String day;

    @ColumnInfo(name="hour")
    String hour;

    @ColumnInfo(name="min")
    String min;

    @ColumnInfo(name="daysRemained")
    double daysRemained;

    public TaskData(int id, String title, String year, String month, String day, String hour, String min)
    {
        this.id= id;
        this.title= title;
        this.year= year;
        this.month= month;
        this.day= day;
        this.hour= hour;
        this.min= min;

        double remained= setDaysRemained();

        this.daysRemained= remained;

    }

    @Ignore
    public TaskData(String title, String year, String month, String day, String hour, String min)
    {
        this.title= title;
        this.year= year;
        this.month= month;
        this.day= day;
        this.hour= hour;
        this.min= min;

        setDaysRemained();
    }

    public double setDaysRemained()
    {
        Calendar dummy= Calendar.getInstance();
        Calendar cal= Calendar.getInstance();
        dummy.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(min));

        long time= (dummy.getTimeInMillis()-cal.getTimeInMillis());

        daysRemained= (double) time/(1000*60*60*24);
        return daysRemained;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
