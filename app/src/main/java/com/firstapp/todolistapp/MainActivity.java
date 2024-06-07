package com.firstapp.todolistapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    Button commitBtn;
    FloatingActionButton addBtn;


    listRecyclerAdapter adapter= new listRecyclerAdapter(this, arrTitle);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       recyclerList= findViewById(R.id.recyclerList);
       addBtn= findViewById(R.id.addBtn);
       recyclerList.setLayoutManager(new LinearLayoutManager(this));

        Dialog dialog= new Dialog(this);


        recyclerList.setAdapter(adapter);

       addBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dialog.setContentView(R.layout.add_title);
               commitBtn= dialog.findViewById(R.id.commitBtn);
               title= dialog.findViewById(R.id.title);
               dialog.show();

               commitBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String Title= title.getText().toString();
                       dialog.dismiss();
                   }
               });
           }
       });
    }
}