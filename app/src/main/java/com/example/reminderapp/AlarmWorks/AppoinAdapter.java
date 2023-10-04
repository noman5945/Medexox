package com.example.reminderapp.AlarmWorks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.R;

import java.util.ArrayList;


public class AppoinAdapter extends RecyclerView.Adapter<AppoinAdapter.AppoinHolder> {

    ArrayList<AppoinItemInfo> appoinItemInfos;

    public static class AppoinHolder extends RecyclerView.ViewHolder {
        public TextView DoctName,Desc,Time,Date,Status;
        public ImageView DocIcon;

        public AppoinHolder(@NonNull View itemView) {
            super(itemView);
            DoctName=itemView.findViewById(R.id.docName);
            Desc=itemView.findViewById(R.id.desc);
            Time=itemView.findViewById(R.id.time);
            Date=itemView.findViewById(R.id.date);
            Status=itemView.findViewById(R.id.status);
            DocIcon=itemView.findViewById(R.id.img1);
        }
    }

    public AppoinAdapter(ArrayList<AppoinItemInfo> appoinItemInfos)
    {
        this.appoinItemInfos=appoinItemInfos;
    }

    @NonNull
    @Override
    public AppoinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.appoinitem,parent,false);
//        AppoinHolder appoinHolder=new AppoinHolder(v);
//        return appoinHolder;
        return new AppoinHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AppoinHolder holder, int position) {
        AppoinItemInfo currentItem=appoinItemInfos.get(position);

        holder.DocIcon.setImageResource(currentItem.getImageRec());
        holder.DoctName.setText(currentItem.getDocName());
        holder.Time.setText(currentItem.getTime());
        holder.Date.setText(currentItem.getDate());
        holder.Desc.setText(currentItem.getDescr());
        holder.Status.setText(currentItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return appoinItemInfos.size();
    }


}
