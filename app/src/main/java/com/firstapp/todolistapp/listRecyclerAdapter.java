package com.firstapp.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listRecyclerAdapter extends RecyclerView.Adapter<listRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<TaskData> arrList= new ArrayList<>();
    MainActivity mainActivity= new MainActivity();
    LinearLayout itemLL;

    public listRecyclerAdapter(Context context, ArrayList<TaskData> arrList)
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int posiition) {

        int position= holder.getAdapterPosition();
        DataModel dataModel= new DataModel(arrList.get(position).getTitle(), arrList.get(position).getYear() ,arrList.get(position).getMonth() ,arrList.get(position).getDay(), arrList.get(position).getHour(), arrList.get(position).getMin());

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
                TaskData taskData= arrList.get(position);
                ((MainActivity) context).startIntent(taskData);
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