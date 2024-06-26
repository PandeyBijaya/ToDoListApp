package com.firstapp.todolistapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class listRecyclerAdapter extends RecyclerView.Adapter<listRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<DataModel> arrList= new ArrayList<>();
    MainActivity mainActivity= new MainActivity();
    LinearLayout itemLL;

    public listRecyclerAdapter(Context context, ArrayList<DataModel> arrList)
    {
        this.context= context;
        this.arrList= arrList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.list_item, parent ,false);
        ViewHolder viewHolder= new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        DataModel dataModel= new DataModel(arrList.get(position).title, arrList.get(position).year ,arrList.get(position).month ,arrList.get(position).day, arrList.get(position).hour, arrList.get(position).min);

        String date= dataModel.getDue();

        int daysRemained= (int)dataModel.getDays();
        String daysLeft;

        if(daysRemained>=30 && daysRemained<60)
        {
            daysLeft= "(1 month left)";
        }
        else if(daysRemained>=60 && daysRemained<90)
        {
            daysLeft= "(2 months left)";
        }
        else if(daysRemained>=90 && daysRemained<365)
        {
            daysLeft= "(>3 months left)";
        }
        else if(daysRemained>=365)
        {
            daysLeft= "(>1 year left)";
        }
        else if(daysRemained<0)
        {
            daysLeft= "(No Time Left)";
        }
        else if(daysRemained<1 && daysRemained>=0)
        {
            date= "Today";
            daysLeft= dataModel.getTime();
        }
        else
        {
            daysLeft= "("+String.valueOf(daysRemained)+" days left)";
        }




        holder.itemTitle.setText(arrList.get(position).title);
        holder.itemDate.setText(date);
        holder.daysLeft.setText(daysLeft);
        itemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, String.valueOf(dataModel.getDays()), Toast.LENGTH_SHORT).show();

                ((MainActivity) context).startIntent(arrList.get(position).title, arrList.get(position).year, arrList.get(position).month, arrList.get(position).day, arrList.get(position).hour, arrList.get(position).min);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle, itemDate, daysLeft;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle= itemView.findViewById(R.id.itemText);
            itemDate= itemView.findViewById(R.id.itemTime);
            daysLeft= itemView.findViewById(R.id.itemTimeleft);
            itemLL= itemView.findViewById(R.id.itemLL);
        }
    }
}