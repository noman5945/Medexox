package com.example.reminderapp.DoctorsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.EmergencyCalling.CallerAdapter;

import java.util.ArrayList;

import com.example.reminderapp.R;

public class DocListAdapter extends RecyclerView.Adapter<DocListAdapter.DocHolder> {

    ArrayList<DocListInfo> docListInfos;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void OnCallClick(int pos);
        void OnMailClick(int pos);
        void OnArrowClick(int pos);
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener){
        mListener=onItemClickListener;
    }

    public static class DocHolder extends RecyclerView.ViewHolder{
        ImageView profImg,phnImg,MailImg;
        ImageView ArrBtn;
        TextView name,desc,phnno,mailad;
        ConstraintLayout constraintLayout;
        CardView cardView;

        public DocHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            name=itemView.findViewById(R.id.name1);
            desc=itemView.findViewById(R.id.desc);
            phnno=itemView.findViewById(R.id.phoneNumber);
            mailad=itemView.findViewById(R.id.mailNumber);

            profImg=itemView.findViewById(R.id.circleImage);
            phnImg=itemView.findViewById(R.id.phoneIcon);
            MailImg=itemView.findViewById(R.id.mailIcon);
            ArrBtn=itemView.findViewById(R.id.arrowBtn);

            constraintLayout=itemView.findViewById(R.id.expandableView);
            cardView=itemView.findViewById(R.id.motherCard);


            ArrBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnArrowClick(position);
                        }
                    }
                }
            });


            phnImg.setOnClickListener(new View.OnClickListener() {
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

            MailImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();

                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnMailClick(position);
                        }
                    }
                }
            });
        }

    }

    public DocListAdapter(ArrayList<DocListInfo> docListInfos)
    {
        this.docListInfos=docListInfos;
    }

    @NonNull
    @Override
    public DocHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.docinfo,parent,false);
        DocHolder holder=new DocHolder(v,mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DocHolder holder, int position) {
            DocListInfo docListInfo=docListInfos.get(position);

            holder.MailImg.setImageResource(docListInfo.getMailImg());
            holder.phnImg.setImageResource(docListInfo.getPhnImg());
            holder.profImg.setImageResource(docListInfo.getProflImg());
            holder.constraintLayout.setId(docListInfo.getExpandablecont());
            holder.cardView.setId(docListInfo.getCardview());
            holder.ArrBtn.setImageResource(docListInfo.getArrbutt());

            holder.name.setText(docListInfo.getName());
            holder.desc.setText(docListInfo.getDesc());
            holder.phnno.setText(docListInfo.getNum());
            holder.mailad.setText(docListInfo.getMail());
    }

    @Override
    public int getItemCount() {
        return docListInfos.size();
    }

}
