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

        holder.itemTitle.setText(arrList.get(position).title);
        holder.itemDate.setText(arrList.get(position).due);
        holder.daysLeft.setText(arrList.get(position).daysLeft);

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