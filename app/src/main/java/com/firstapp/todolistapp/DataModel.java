package com.firstapp.todolistapp;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataModel {

    String title, month, due,year,day;
    int daysRemained, id;
    MainActivity mainActivity= new MainActivity();

    public DataModel() {
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

    public DataModel(String title, String year, String month, String day)
    {
        this.due=(mainActivity.wordMonth(Integer.parseInt(month))+" " +day);
        daysRemained= ((365*(Integer.parseInt(year)-getYear())+30 * Integer.parseInt(month)+Integer.parseInt(day))-30* getMonth() -getDay());

    }
    public DataModel(int id, String title, String year, String month, String day)
    {
        this.title= title;
        this.year= year;
        this.month= month;
        this.day= day;
        this.id= id;
    }
}
