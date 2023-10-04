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

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineHolder> {

    ArrayList<MedicineInfo> medicineInfos;

    public static class MedicineHolder extends RecyclerView.ViewHolder {
        public TextView medicineName, date, time;
        public ImageView medIcon;

        public MedicineHolder(@NonNull View itemView) {
            super(itemView);
            medicineName = itemView.findViewById(R.id.medName);
            date = itemView.findViewById(R.id.medicineDate);
            time = itemView.findViewById(R.id.medicineTime);
            medIcon = itemView.findViewById(R.id.img);
        }
    }

    public MedicineAdapter(ArrayList<MedicineInfo> medicineInfo) {
        this.medicineInfos = medicineInfo;
    }


    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.medicineitem,parent,false);
//        MedicineHolder medicineHolder = new MedicineHolder(v);
//        return medicineHolder;
        return new MedicineHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineAdapter.MedicineHolder holder, int position) {
        MedicineInfo currentMedicItem = medicineInfos.get(position);

        holder.medIcon.setImageResource(currentMedicItem.getImage());
        holder.medicineName.setText(currentMedicItem.getMedicineName());
        holder.time.setText(currentMedicItem.getTime());
        holder.date.setText(currentMedicItem.getDate());
    }

    @Override
    public int getItemCount() {
        return medicineInfos.size();
    }
}
