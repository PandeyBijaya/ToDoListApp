package com.firstapp.todolistapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class listRecyclerAdapter extends RecyclerView.Adapter<listRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<DataModel> arrList= new ArrayList<>();
    MainActivity mainActivity= new MainActivity();
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


        DataModel dataModel= new DataModel(arrList.get(position).title, arrList.get(position).year ,arrList.get(position).month ,arrList.get(position).day);

        String date= dataModel.due;

        int daysRemained= dataModel.daysRemained;
        String daysLeft;

        if(daysRemained>30 && daysRemained<60)
        {
            daysLeft= "(1 month left)";
        }
        else if(daysRemained>60 && daysRemained<90)
        {
            daysLeft= "(2 months left)";
        }
        else if(daysRemained>90 && daysRemained<365)
        {
            daysLeft= "(>3 months left)";
        }
        else if(daysRemained>365)
        {
            daysLeft= "(>1 year left)";
        }
        else if(daysRemained<=0)
        {
            daysLeft= "(No Time Left)";
        }
        else
        {
            daysLeft= "("+Integer.toString(daysRemained)+" days left)";
        }

        holder.itemTitle.setText(arrList.get(position).title);
        holder.itemDate.setText(date);
        holder.daysLeft.setText(daysLeft);

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
        }
    }
}