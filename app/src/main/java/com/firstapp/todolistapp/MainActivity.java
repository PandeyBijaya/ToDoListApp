package com.firstapp.todolistapp;

import static android.Manifest.permission_group.CALENDAR;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ArrayList<TaskData> arrTitle;
    RecyclerView recyclerList;
    EditText title;
    TextView date, itemTime, noTask, time;
    Button commitBtn, cancelBtn;
    FloatingActionButton addBtn;
    ImageView calendar, clock;
    String month, Title, year, day, hour, min;
    int currentYear, currentMonth, currentDay, currentHour, currentMin;
    Dialog dialog;
    Toolbar toolbar;
    Calendar cal= Calendar.getInstance();
    DatabaseHelper databaseHelper;
    ActivityResultLauncher<Intent> updateLauncher;


    listRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateLauncher();
        //Finding id of activity main layout and setting recycler view
        recyclerList = findViewById(R.id.recyclerList);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));
        addBtn = findViewById(R.id.addBtn);
        noTask = findViewById(R.id.noTask);
        toolbar = findViewById(R.id.toolBar);

        init();

    }

    public void activateLauncher()
    {
        updateLauncher= registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if(result.getResultCode()==RESULT_OK)
                        {
                            init();
                        }
                    }
                }
        );

    }
    public void  init()
    {
        //Setting RecyclerList
        databaseHelper= DatabaseHelper.getDB(MainActivity.this);
        arrTitle = new ArrayList<TaskData>();
        arrTitle = (ArrayList<TaskData>) databaseHelper.taskDao().fetchData();
        adapter = new listRecyclerAdapter(MainActivity.this, arrTitle);
        recyclerList.setAdapter(adapter);


        if (arrTitle.isEmpty())
            noTask.setVisibility(View.VISIBLE);

        //Getting current date and time
        currentYear = cal.get(Calendar.YEAR);
        currentMonth = cal.get(Calendar.MONTH);
        currentDay = cal.get(Calendar.DAY_OF_MONTH);



        //Setting toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("To Do List");

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                details();
            }
        });

        //Setting the notifications here




    }

    public void details() {
        dialog = new Dialog(MainActivity.this);
        //Setting dialog and finding ids of listItems
        dialog.setContentView(R.layout.add_title);
        commitBtn = dialog.findViewById(R.id.commitBtn);
        title = dialog.findViewById(R.id.title);
        calendar = dialog.findViewById(R.id.calendar);
        date = dialog.findViewById(R.id.date);
        itemTime = dialog.findViewById(R.id.itemTime);
        date = dialog.findViewById(R.id.date);
        cancelBtn = dialog.findViewById(R.id.cancelBtn);
        time= dialog.findViewById(R.id.time);
        clock= dialog.findViewById(R.id.clock);
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

        clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTime();
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userTime();
            }
        });

        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.Title = title.getText().toString();

                if (time.getText().toString().isEmpty() || date.getText().toString().isEmpty() || ("".equals(Title)))
                    Toast.makeText(getApplicationContext(), "Please enter all the req field", Toast.LENGTH_LONG).show();
                else {
                    TaskData data= new TaskData(Title, year, month, day, hour, min );
                    databaseHelper.taskDao().insertData(data);
                    NotificationHelper notificationHelper= new NotificationHelper();

                    notificationHelper.scheduleNotif(MainActivity.this, data);
                    startIntent(data);
                    Toast.makeText(MainActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    init();
                }
            }
        });

    }



    private void dueTime() {
        DatePickerDialog dateDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                month = Integer.toString(i1);

                MainActivity.this.year = Integer.toString(i);
                MainActivity.this.day = Integer.toString(i2);

                date.setText(wordMonth(i1) + " " + day + ", " + year);
            }

        }, currentYear, currentMonth, currentDay);
        dateDialog.show();
    }

    public String wordMonth(int i1) {

        String[] mon= new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        return mon[i1];

    }

    public void startIntent(TaskData data) {

        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);


        intent.putExtra("id", data.getId());
        intent.putExtra("title", data.getTitle());
        intent.putExtra("year", data.getYear());
        intent.putExtra("month", data.getMonth());
        intent.putExtra("day", data.getDay());
        intent.putExtra("hour", data.getHour());
        intent.putExtra("min", data.getMin());



        updateLauncher.launch(intent);

    }
    private void userTime() {

        TimePickerDialog timePicker= new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {


                    hour = Integer.toString(i);
                    min = Integer.toString(i1);

                    time.setText(hour + ":" + min);


            }

        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
        timePicker.show();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }
}

