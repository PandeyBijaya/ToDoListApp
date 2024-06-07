package com.firstapp.todolistapp;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataModel {

    String title, month, due, daysLeft;
    Context context;
    MainActivity mainActivity= new MainActivity();

    public DataModel(Context context)
    {
        this.context= context;
    }
    public int getDay(){
        return Integer.parseInt(new SimpleDateFormat("dd",Locale.getDefault()).format(new Date()));

    }
    public int getMonth()
    {
        return Integer.parseInt(new SimpleDateFormat("MM",Locale.getDefault()).format(new Date()));

    }
    public int getYear()
    {
        return Integer.parseInt(new SimpleDateFormat("yyyy",Locale.getDefault()).format(new Date()));

    }

    public DataModel( String title)
    {
        this.title= title;
    }
    public DataModel(String title, String year, String month, String day)
    {
        this.title=title;
        this.month=month;
        this.due=(mainActivity.wordMonth(Integer.parseInt(month))+" " +day);
        int daysRemained= ((365*(Integer.parseInt(year)-getYear())+30 * Integer.parseInt(month)+Integer.parseInt(day))-30* getMonth() -getDay());

        if(daysRemained>30 && daysRemained<60)
        {
            this.daysLeft= "(1 month left)";
        }
        else if(daysRemained>60 && daysRemained<90)
        {
            this.daysLeft= "(2 months left)";
        }
        else if(daysRemained>90 && daysRemained<365)
        {
            this.daysLeft= "(>3 months left)";
        }
        else if(daysRemained>365)
        {
            this.daysLeft= "(>1 year left)";
        }
        else if(daysRemained<=0)
          {
         this.daysLeft= "(No Time Left)";
            }
        else
        {
            this.daysLeft= "("+Integer.toString(daysRemained)+" days left)";
        }


    }
}
