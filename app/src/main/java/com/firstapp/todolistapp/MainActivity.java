package com.firstapp.todolistapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> arrTitle= new ArrayList<>();
    RecyclerView recyclerList;
    EditText title;
    TextView date, itemTime;
    Button commitBtn;
    FloatingActionButton addBtn;
    ImageView calendar;
    String month,Title,year,day;
    int currentYear, currentMonth, currentDay;
    Dialog dialog;



    listRecyclerAdapter adapter= new listRecyclerAdapter(this, arrTitle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    //Finding id of activity main layout and setting recycler view

       recyclerList= findViewById(R.id.recyclerList);
       addBtn= findViewById(R.id.addBtn);
       recyclerList.setLayoutManager(new LinearLayoutManager(this));
       recyclerList.setAdapter(adapter);

       //Getting current date and time from DataModel
        DataModel dataModel= new DataModel(MainActivity.this);

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
        dialog.show();



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
                    arrTitle.add(new DataModel(Title, year, month, day));

                    adapter.notifyItemInserted(arrTitle.size()-1);
                    recyclerList.scrollToPosition(arrTitle.size() - 1);

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

                MainActivity.this.year=String.valueOf(i);
                MainActivity.this.day=String.valueOf(i2);



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

    }

