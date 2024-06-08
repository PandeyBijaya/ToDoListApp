package com.firstapp.todolistapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateActivity extends AppCompatActivity {
    String Title,year,month,day;
    int position;
    TextView updateTitle, updateDate;
    DataModel dataModel;
    Button updateDelete;
    SQLiteDatabase database;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateTitle = findViewById(R.id.updateTitle);
        updateDate= findViewById(R.id.updateDate);
        updateDelete= findViewById(R.id.updateDelete);

        //Bundle passing
        Intent getIntent= getIntent();

        Title= getIntent.getStringExtra("title");
        year= getIntent.getStringExtra("year");
        month= getIntent.getStringExtra("month");
        day= getIntent.getStringExtra("day");
        position= getIntent.getIntExtra("position", 0);

        dataModel= new DataModel(Title, year, month, day);

        updateTitle.setText(Title);
        updateDate.setText(dataModel.due+" "+year);

        //Deleting Task undergoing
        database= new SQLiteDatabase(this);
        updateDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask();
            }
        });

    }
    //Deleting Task Complete
    public void setContext(Context context)
    {
        this.context= context;
    }

    public void deleteTask()
    {

            database.deleteTask(Title);


                Intent intent= new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);

    }

}
