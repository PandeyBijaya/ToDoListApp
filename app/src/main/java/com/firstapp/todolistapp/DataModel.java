package com.firstapp.todolistapp;

public class DataModel {

    String title, month, day, daysLeft;

    public DataModel( String data)
    {
        this.title= data;
    }
    public DataModel(String month, String day)
    {
        this.month=month;
        this.day=day;
    }
}
