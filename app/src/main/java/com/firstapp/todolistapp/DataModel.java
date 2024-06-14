package com.firstapp.todolistapp;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataModel {

    String title, month, due,year,day,timeLeft, hour, min;
    int daysRemained, id;
    MainActivity mainActivity= new MainActivity();

    public DataModel() {
    }

    public int getDay(){
        return Integer.parseInt(new SimpleDateFormat("dd",Locale.getDefault()).format(new Date()));

    }
    public int getMonth()
    {
        return Integer.parseInt((new SimpleDateFormat("MM",Locale.getDefault()).format(new Date())))-1;

    }
    public int getYear()
    {
        return Integer.parseInt(new SimpleDateFormat("yyyy",Locale.getDefault()).format(new Date()));

    }
    public int getMin(){
        return Integer.parseInt(new SimpleDateFormat("mm",Locale.getDefault()).format(new Date()));

    }
    public int getHour(){
        return Integer.parseInt(new SimpleDateFormat("HH",Locale.getDefault()).format(new Date()));

    }

    public DataModel(String title, String year, String month, String day, String hour, String min)
    {
        this.title= title;
        this.year= year;
        this.month= month;
        this.day= day;
        this.hour= hour;
        this.min= min;

        int TotalDays= (365*(Integer.parseInt(year))+(30 *( Integer.parseInt(month)))+Integer.parseInt(day));
        int completedDays= (365*getYear()+(30 *(getMonth()))+getDay());
        daysRemained= TotalDays- completedDays;

            this.due=(mainActivity.wordMonth(Integer.parseInt(month))+" " +day);


        int totalTime= Integer.parseInt(hour)*60+ Integer.parseInt(min)- 60*getHour()-getMin();

        int hourLeft= totalTime/60;
        int minLeft=totalTime%60;

        timeLeft="("+ Integer.toString(hourLeft)+" hr "+ Integer.toString(minLeft)+" min left)";
    }

}
