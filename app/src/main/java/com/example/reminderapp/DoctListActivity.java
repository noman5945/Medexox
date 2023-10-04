package com.example.reminderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.reminderapp.DoctorsList.DocListAdapter;
import com.example.reminderapp.DoctorsList.DocListInfo;
import com.example.reminderapp.EmergencyCalling.Caller;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctListActivity extends AppCompatActivity {
    RecyclerView rcl;
    RecyclerView.LayoutManager mlayoutManager;
    DocListAdapter docListAdapter;
    ConstraintLayout constraintLayout;
    CardView cardView;
    ImageView arrbtn;

    ArrayList<DocListInfo> docListInfos=new ArrayList<>();
    ArrayList<String> emailList=new ArrayList<>();
    ArrayList<String> contList=new ArrayList<>();

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doct_list);

        Intent intent=getIntent();
        String type=intent.getStringExtra("typ");

        rcl=(RecyclerView)findViewById(R.id.rclview2);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("DoctorList").child(type);

        getData(databaseReference,DoctListActivity.this);
    }

    public void getData(DatabaseReference dbf, Context context)
    {
        dbf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                docListInfos.clear();
                int i=0;
                for(DataSnapshot dsp:snapshot.getChildren())
                {
                    i++;
                    String nam=dsp.child("docname").getValue(String.class);
                    String num=dsp.child("phnNo").getValue(String.class);
                    String desc=dsp.child("academicInfo").getValue(String.class)+"\n"+dsp.child("Hospital").getValue(String.class);
                    String mail=dsp.child("docemail").getValue(String.class);
                    String id=dsp.child("id").getValue(String.class);
                    contList.add(num);
                    emailList.add(mail);
                    docListInfos.add(new DocListInfo(nam,num,desc,mail,R.drawable.an_profile,R.drawable.ic_baseline,R.drawable.ic_baseline_email_24,R.id.expandableView,R.id.motherCard,R.drawable.ic_baseline_keyboard_arrow_down_24));
                    //Toast.makeText(context,id,Toast.LENGTH_SHORT).show();
                }
                rcl.setHasFixedSize(true);
                mlayoutManager=new LinearLayoutManager(context);
                docListAdapter=new DocListAdapter(docListInfos);
                rcl.setLayoutManager(mlayoutManager);
                rcl.setAdapter(docListAdapter);

                Toast.makeText(context,String.valueOf(docListInfos.size()),Toast.LENGTH_LONG).show();
                docListAdapter.setItemClickListener(new DocListAdapter.OnItemClickListener() {
                    @Override
                    public void OnCallClick(int pos) {
                        String num=contList.get(pos);
                        Caller docCall=new Caller();
                        docCall.calluser(num,DoctListActivity.this);
                    }

                    @Override
                    public void OnMailClick(int pos) {
                        String email=emailList.get(pos);
                        Intent intent=new Intent(DoctListActivity.this,EmailBodyActivity.class);
                        intent.putExtra("mail",email);
                        intent.putExtra("prev","1");
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void OnArrowClick(int pos) {
                        int conID=docListInfos.get(pos).getExpandablecont();
                        int cardID=docListInfos.get(pos).getCardview();
                        int buttID=docListInfos.get(pos).getArrbutt();

                        cardView=findViewById(cardID);
                        constraintLayout=findViewById(conID);
                        arrbtn=findViewById(buttID);

                        if(constraintLayout.getVisibility()==View.GONE)
                        {
                            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
                            constraintLayout.setVisibility(View.VISIBLE);
                            docListAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            TransitionManager.beginDelayedTransition(cardView,new AutoTransition());
                            constraintLayout.setVisibility(View.GONE);
                            docListAdapter.notifyDataSetChanged();
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addy(View view) {
        finish();
        startActivity(new Intent(DoctListActivity.this,DoctAddActivity.class));
    }

    public void back(View view) {
        finish();
        startActivity(new Intent(DoctListActivity.this,HealthStatus.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DoctListActivity.this,HealthStatus.class);
        startActivity(intent);

        finish();
    }
}