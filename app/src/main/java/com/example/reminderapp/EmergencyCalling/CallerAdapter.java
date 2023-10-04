package com.example.reminderapp.EmergencyCalling;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.R;

import java.util.ArrayList;

public class CallerAdapter extends RecyclerView.Adapter<CallerAdapter.MyHolder> {

    ArrayList<ItemInfo> itemInfos;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void OnItemClick(int pos);
        void OnCallClick(int pos);
        void OnSMSClick(int pos);
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener){
        mListener=onItemClickListener;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
        public TextView adname,adnum;
        public ImageView adimg,mcall,sms;
        public MyHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            adname=itemView.findViewById(R.id.name1);
            adnum=itemView.findViewById(R.id.num1);
            adimg=itemView.findViewById(R.id.img1);
            mcall=itemView.findViewById(R.id.cal);
            sms=itemView.findViewById(R.id.sms);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }

                }
            });

            mcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnCallClick(position);
                        }
                    }
                }
            });

            sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnSMSClick(position);
                        }
                    }
                }
            });
        }
    }

    public CallerAdapter(ArrayList<ItemInfo> itemInfos)
    {
        this.itemInfos=itemInfos;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        MyHolder holder=new MyHolder(v,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ItemInfo currentItem=itemInfos.get(position);

        holder.adimg.setImageResource(currentItem.getImgRec());
        holder.mcall.setImageResource(currentItem.getCalImg());
        holder.sms.setImageResource(currentItem.getSmsImg());
        holder.adname.setText(currentItem.getName());
        holder.adnum.setText(currentItem.getNum());
    }

    @Override
    public int getItemCount() {
        return itemInfos.size();
    }

}
