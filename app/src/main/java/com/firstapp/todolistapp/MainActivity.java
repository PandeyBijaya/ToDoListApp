package com.firstapp.todolistapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> arrTitle= new ArrayList<>();
    RecyclerView recyclerList;
    EditText title;
    TextView date, itemTime,noTask;
    Button commitBtn, cancelBtn;
    FloatingActionButton addBtn;
    ImageView calendar;
    String month,Title,year,day;
    int currentYear, currentMonth, currentDay;
    Dialog dialog;
    SQLiteDatabase database;
    DataModel dataModel;


    listRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linking database here

                database= new SQLiteDatabase(this);
                new UpdateActivity().setContext(getApplicationContext());


    //Finding id of activity main layout and setting recycler view

       recyclerList= findViewById(R.id.recyclerList);
        arrTitle= database.fetchData();
        adapter= new listRecyclerAdapter(MainActivity.this, arrTitle);
        recyclerList.setAdapter(adapter);
       addBtn= findViewById(R.id.addBtn);
       noTask= findViewById(R.id.noTask);

       recyclerList.setLayoutManager(new LinearLayoutManager(this));


       if(arrTitle.size()>0)
           noTask.setVisibility(View.INVISIBLE);

       //Getting current date and time from DataModel
        dataModel= new DataModel();

        currentYear= dataModel.getYear();
        currentMonth= (dataModel.getMonth());
        currentDay= (dataModel.getDay());
        dialog= new Dialog(this);



       addBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               details();
           }
       });





    }
    public void details()
    {
        //Setting dialog and finding ids of listItems
        dialog.setContentView(R.layout.add_title);
        commitBtn= dialog.findViewById(R.id.commitBtn);
        title= dialog.findViewById(R.id.title);
        calendar= dialog.findViewById(R.id.calendar);
        date= dialog.findViewById(R.id.date);
        itemTime= dialog.findViewById(R.id.itemTime);
        date= dialog.findViewById(R.id.date);
        cancelBtn= dialog.findViewById(R.id.cancelBtn);
        dialog.show();


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dueTime();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dueTime();
            }
        });
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.Title= title.getText().toString();

                if(("").equals(date.getText().toString()) || ("".equals(Title)))
                    Toast.makeText(getApplicationContext(), "Please enter all the req field", Toast.LENGTH_LONG).show();
                else {

                    DataModel dataModel1= new DataModel(Title, year, month, day);
                    database.insertData(Title, Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day), dataModel1.daysRemained);
                    addInRecyclerView();
                    dialog.dismiss();
                }
            }
        });
    }

    private void dueTime() {
        DatePickerDialog dateDialog= new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                month= Integer.toString(i1);

                MainActivity.this.year= Integer.toString(i);
                MainActivity.this.day= Integer.toString(i2);

                date.setText(wordMonth(i1)+" "+day+", "+year);
            }

        }, currentYear, currentMonth, currentDay);
        dateDialog.show();
    }

    public String wordMonth(int i1) {
        if(i1==0)
            return ("Jan");
        else if(i1==1)
            return "Feb";
        else if(i1==2)
            return "Mar";
        else if(i1==3)
            return "Apr";
        else if(i1==4)
            return "May";
        else if(i1==5)
            return "Jun";
        else if(i1==6)
            return "Jul";
        else if(i1==7)
            return "Aug";
        else if(i1==8)
            return "Sep";
        else if(i1==9)
            return "Oct";
        else if(i1==10)
            return "Nov";
        else if(i1==11)
            return "Dec";
        else
            return "";

        }
        public void startIntent(String Title, String year, String month, String day, int position)
        {

                    Intent intent= new Intent(getApplicationContext(), UpdateActivity.class);



                    intent.putExtra("title", Title);
                    intent.putExtra("year", year);
                    intent.putExtra("month", month);
                    intent.putExtra("day", day);
                    intent.putExtra("position", position);



                    startActivity(intent);



        }
        /*public void updateDelete(String title, int position)
        {
            database.deleteTask(title);

            arrTitle.clear();
            arrTitle= database.fetchData();

            adapter.notifyItemRemoved(position);
            recyclerList.scrollToPosition(position);
        }*/

        public void addInRecyclerView()
        {
            arrTitle.clear();
            arrTitle= database.fetchData();

            adapter.notifyItemInserted(arrTitle.size()-1);
            recyclerList.scrollToPosition(arrTitle.size()-1);

        }
    public void changeInRecyclerView()
    {
        arrTitle.clear();
        arrTitle= database.fetchData();

        adapter.notifyItemChanged(arrTitle.size()-1);
        recyclerList.scrollToPosition(arrTitle.size()-1);

    }
    public void removeInRecyclerView()
    {
        arrTitle.clear();
        arrTitle= database.fetchData();

        adapter.notifyItemRemoved(arrTitle.size()-1);
        recyclerList.scrollToPosition(arrTitle.size()-1);

    }
 }

