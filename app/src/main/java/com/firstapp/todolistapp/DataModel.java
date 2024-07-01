package com.firstapp.todolistapp;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataModel {

    String title, month,year,day, hour, min;
    MainActivity mainActivity= new MainActivity();
    Calendar cal= Calendar.getInstance();

    public DataModel(String title, String year, String month, String day, String hour, String min)
    {
        this.title= title;
        this.year= year;
        this.month= month;
        this.day= day;
        this.hour= hour;
        this.min= min;
    }

    public double getDays()
    {
        Calendar dummy= Calendar.getInstance();
        dummy.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(min));

        long time= (dummy.getTimeInMillis()-cal.getTimeInMillis());

        double TotalHour= (double) Integer.parseInt(hour) + (double) Integer.parseInt(min) /60 - (double)cal.get(Calendar.HOUR_OF_DAY)- (double) cal.get(Calendar.MINUTE) /60;
        return (double)time/(1000*60*60*24);
    }

    public String getDue()
    {
        String due=(mainActivity.wordMonth(Integer.parseInt(month))+" " +day);
        return due;
    }

    public String getTime()
    {
        int totalTime= Integer.parseInt(hour)*60+ Integer.parseInt(min)- 60*cal.get(Calendar.HOUR_OF_DAY)-cal.get(Calendar.MINUTE);

        int hourLeft= totalTime/60;
        int minLeft=totalTime%60;

        if(totalTime>0)
            return "("+ Integer.toString(hourLeft)+" hr "+ Integer.toString(minLeft)+" min left)";

        return "(Due ended)";
    }

}
