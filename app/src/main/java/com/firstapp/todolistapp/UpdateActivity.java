package com.firstapp.todolistapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    String Title, year, month, day, oldTitle, oldDate, hour, min;
    TextView title, Date, updateDate, taskText, Time, updateTime;
    EditText updateTitle;
    DataModel dataModel, dataModel1;
    static TaskData taskData;
    Button deleteBtn, editBtn, saveBtn, goBackBtn;
    SQLiteDatabase database;
    ImageView calendarImg, clockImg;
    LinearLayout updateLL;
    Toolbar toolbar;
    Calendar cal = Calendar.getInstance();
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.Title);
        Date = findViewById(R.id.Date);
        updateTitle = findViewById(R.id.updateTitle);
        updateDate = findViewById(R.id.updateDate);
        deleteBtn = findViewById(R.id.deleteBtn);
        editBtn = findViewById(R.id.editBtn);
        saveBtn = findViewById(R.id.saveBtn);
        goBackBtn = findViewById(R.id.goBackBtn);
        calendarImg = findViewById(R.id.calendarImg);
        updateLL = findViewById(R.id.updateLL);
        taskText = findViewById(R.id.taskText);
        toolbar = findViewById(R.id.toolBar);
        Time = findViewById(R.id.Time);
        updateTime = findViewById(R.id.updateTime);
        clockImg = findViewById(R.id.clockImg);


        //Bundle passing
        Intent getIntent = getIntent();

        Title = getIntent.getStringExtra("title");
        oldTitle = Title;
        year = getIntent.getStringExtra("year");
        month = getIntent.getStringExtra("month");
        day = getIntent.getStringExtra("day");
        hour = getIntent.getStringExtra("hour");
        min = getIntent.getStringExtra("min");
        int id=  getIntent.getIntExtra("id",0);

        dataModel = new DataModel(Title, year, month, day, hour, min);
        taskData= new TaskData(id,Title, year, month, day, hour, min);

        title.setText(Title);
        updateTitle.setVisibility(View.INVISIBLE);
        Date.setText(dataModel.getDue() + " " + year);
        Time.setText(hour + ":" + min);
        oldDate = Date.getText().toString();
        updateDate.setText(Date.getText().toString());
        updateDate.setVisibility(View.INVISIBLE);
        updateTime.setVisibility(View.INVISIBLE);
        saveBtn.setVisibility(View.INVISIBLE);


        //Setting ToolBar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //Deleting Task undergoing
        database = new SQLiteDatabase(this);
        databaseHelper= DatabaseHelper.getDB(this);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask();
            }
        });
        //Deleting Task Complete
        //Editing Task Undergoing

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                updateTitle.setVisibility(View.VISIBLE);
                goBackBtn.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                updateDate.setVisibility(View.VISIBLE);
                Date.setVisibility(View.INVISIBLE);
                Time.setVisibility(View.INVISIBLE);
                editBtn.setVisibility(View.INVISIBLE);
                deleteBtn.setVisibility(View.INVISIBLE);
                saveBtn.setVisibility(View.VISIBLE);
                updateTime.setVisibility(View.VISIBLE);
                updateTitle.setText(title.getText().toString());
                updateTime.setText(Time.getText().toString());


                updateDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDate();
                    }
                });

                calendarImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDate();
                    }
                });

                updateTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getTime();
                    }
                });

                clockImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getTime();
                    }
                });


                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (updateTitle.getText().toString().isEmpty()) {
                            Toast.makeText(UpdateActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                        } else {
                            title.setText(updateTitle.getText().toString());
                            Time.setText(updateTime.getText().toString());
                            Date.setText(updateDate.getText().toString());


                        }


                        updateTitle.setVisibility(View.INVISIBLE);
                        goBackBtn.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        updateDate.setVisibility(View.INVISIBLE);
                        Date.setVisibility(View.VISIBLE);
                        editBtn.setVisibility(View.VISIBLE);
                        deleteBtn.setVisibility(View.VISIBLE);
                        saveBtn.setVisibility(View.INVISIBLE);
                        updateTime.setVisibility(View.INVISIBLE);
                        Time.setVisibility(View.VISIBLE);

                        databaseHelper.taskDao().updateTask(taskData);
                        Toast.makeText(UpdateActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(UpdateActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }


    public void deleteTask() {



        databaseHelper.taskDao().deleteTask(taskData);

        Toast.makeText(UpdateActivity.this, "Deleted the task: " + oldTitle, Toast.LENGTH_SHORT).show();


        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);

    }

    private void getTime() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                UpdateActivity.this.hour = Integer.toString(i);
                UpdateActivity.this.min = Integer.toString(i1);

                taskData.min=min;
                taskData.hour=hour;
                taskData.daysRemained= taskData.setDaysRemained();

                updateTime.setText(hour + ":" + min);
            }
        }, (Calendar.getInstance().get(Calendar.HOUR)), (Calendar.getInstance().get(Calendar.MINUTE)), true);
        timePickerDialog.show();
    }

    public void getDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = Integer.toString(i);
                month = Integer.toString(i1);
                day = Integer.toString(i2);

                dataModel1 = new DataModel(Title, year, month, day, hour, min);

                taskData.year= year;
                taskData.month=month;
                taskData.day=day;
                taskData.daysRemained= taskData.setDaysRemained();


                if (dataModel1.getDue().equals("Today"))
                    updateDate.setText(dataModel1.getDue());
                else
                    updateDate.setText(dataModel1.getDue() + " " + year);


            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (goBackBtn.getVisibility() == View.VISIBLE) {

            if (item.getItemId() == android.R.id.home) {
                finish();
            }
        } else {
            updateTitle.setVisibility(View.INVISIBLE);
            goBackBtn.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            updateDate.setVisibility(View.INVISIBLE);
            Date.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);
            updateTime.setVisibility(View.INVISIBLE);
            Time.setVisibility(View.VISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

}
