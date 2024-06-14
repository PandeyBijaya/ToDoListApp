package com.firstapp.todolistapp;

import android.app.DatePickerDialog;
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
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {
    String Title,year,month,day,oldTitle, oldDate;
    int position;
    TextView title, Date, updateDate, taskText;
    EditText updateTitle;
    DataModel dataModel, dataModel1;
    Button deleteBtn, editBtn, saveBtn,goBackBtn;
    SQLiteDatabase database;
    Context context;
    ImageView calendarImg;
    LinearLayout updateLL;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.Title);
        Date= findViewById(R.id.Date);
        updateTitle = findViewById(R.id.updateTitle);
        updateDate= findViewById(R.id.updateDate);
        deleteBtn= findViewById(R.id.deleteBtn);
        editBtn= findViewById(R.id.editBtn);
        saveBtn= findViewById(R.id.saveBtn);
        goBackBtn= findViewById(R.id.goBackBtn);
        calendarImg= findViewById(R.id.calendarImg);
        updateLL= findViewById(R.id.updateLL);
        taskText = findViewById(R.id.taskText);
        toolbar = findViewById(R.id.toolBar);




        //Bundle passing
        Intent getIntent= getIntent();

        Title= getIntent.getStringExtra("title");
        oldTitle= Title;
        year= getIntent.getStringExtra("year");
        month= getIntent.getStringExtra("month");
        day= getIntent.getStringExtra("day");
        position= getIntent.getIntExtra("position", 0);

        dataModel= new DataModel(Title, year, month, day);

        title.setText(Title);
        updateTitle.setVisibility(View.INVISIBLE);
        Date.setText(dataModel.due+" "+year);
        oldDate= Date.getText().toString();
        updateDate.setText(Date.getText().toString());
        updateDate.setVisibility(View.INVISIBLE);
        saveBtn.setVisibility(View.INVISIBLE);

        //Setting ToolBar
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setTitle("To Do List");

        //Deleting Task undergoing
        database= new SQLiteDatabase(this);
        int id= database.getId(oldTitle);
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
                    editBtn.setVisibility(View.INVISIBLE);
                    deleteBtn.setVisibility(View.INVISIBLE);
                    saveBtn.setVisibility(View.VISIBLE);
                    updateTitle.setText(title.getText().toString());




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


                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(updateTitle.getText().toString().equals(""))
                        {
                            Toast.makeText(UpdateActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                        else {


                            dataModel1 = new DataModel(Title, year, month, day);
                            title.setText(updateTitle.getText().toString());

                            Date.setText(dataModel1.due + " " + year);


                            updateTitle.setVisibility(View.INVISIBLE);
                            goBackBtn.setVisibility(View.VISIBLE);
                            title.setVisibility(View.VISIBLE);
                            updateDate.setVisibility(View.INVISIBLE);
                            Date.setVisibility(View.VISIBLE);
                            editBtn.setVisibility(View.VISIBLE);
                            deleteBtn.setVisibility(View.VISIBLE);
                            saveBtn.setVisibility(View.INVISIBLE);


                            database.updateTask(id, title.getText().toString(), Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), dataModel.daysRemained);
                            Toast.makeText(UpdateActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();


                        }

                    }
                });

            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(UpdateActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                newMainActivity();

            }
        });


    }




    public void setContext(Context context)
    {
        this.context= context;
    }

    public void deleteTask()
    {

            database.deleteTask(Title);

        Toast.makeText(UpdateActivity.this, "Deleted the task: "+ oldTitle, Toast.LENGTH_SHORT).show();


        Intent intent= new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);

    }

    public void getDate()
    {
        DatePickerDialog datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year= Integer.toString(i);
                month= Integer.toString(i1);
                day= Integer.toString(i2);

                dataModel1= new DataModel(Title, year, month, day);
                updateDate.setText(dataModel1.due+" "+year);

            }
        }, dataModel.getYear(), dataModel.getMonth(), dataModel.getDay());
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(goBackBtn.getVisibility()== View.VISIBLE) {

            if (item.getItemId() == android.R.id.home) {
                newMainActivity();
            }
        }
        else
        {
            updateTitle.setVisibility(View.INVISIBLE);
            goBackBtn.setVisibility(View.VISIBLE);
            title.setVisibility(View.VISIBLE);
            updateDate.setVisibility(View.INVISIBLE);
            Date.setVisibility(View.VISIBLE);
            editBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.INVISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    private void newMainActivity() {
        finish();

        Intent intent= new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
